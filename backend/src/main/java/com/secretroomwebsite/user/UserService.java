package com.secretroomwebsite.user;

import com.secretroomwebsite.authentication.*;
import com.secretroomwebsite.emailClient.EmailService;
import com.secretroomwebsite.exception.ResourceNotFoundException;
import com.secretroomwebsite.exception.TokenExpiredException;
import com.secretroomwebsite.exception.UserAlreadyExistsException;
import com.secretroomwebsite.exception.UserCreationException;
import com.secretroomwebsite.order.OrderService;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final KeycloakAdminService keycloakAdminService;
    private final KeycloakTokenService keycloakTokenService;

    private final OrderService orderService;

    @Value("${keycloak.user-realm}")
    private String userRealm;

    @Value("${keycloak.server-url}")
    private String serverUrl;

    @Value("${keycloak.webclient-id}")
    private String clientId;

    @Value("${frontend.reset-password-url}")
    private String resetPasswordUrl ;

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final EmailService emailService;


    @Autowired
    public UserService(KeycloakAdminService keycloakAdminService,
                       KeycloakTokenService keycloakTokenService,
                       OrderService orderService, PasswordResetTokenRepository passwordResetTokenRepository, EmailService emailService) {

        this.keycloakAdminService = keycloakAdminService;
        this.keycloakTokenService = keycloakTokenService;
        this.orderService = orderService;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.emailService = emailService;
    }

    public UserResponseDTO createUser(UserDTO userDTO) {
        // Step 1: Create user

        UserRepresentation user = createUserRepresentation(userDTO);
        UsersResource usersResource = keycloakAdminService.getInstance().realm(userRealm).users();
        Response response = usersResource.create(user);
        handleCreateUserResponse(response, userDTO.email(), userDTO.password());

        // Step 2: Get access token
        KeycloakTokenResponse responseToken = this.keycloakTokenService.fetchAccessToken(userDTO.email(), userDTO.password());

        // Step 3: Extract access token
        String accessToken = responseToken.access_token();

        String refreshToken = responseToken.refresh_token();

        // Step 4: Send request and get user info
        ResponseAuthKeycloak userResponse = getUserInfo(accessToken);

        // Step 5: Return response DTO
        return new UserResponseDTO(accessToken, refreshToken , userResponse.given_name(), userResponse.family_name(), userResponse.email());

    }

    private ResponseAuthKeycloak getUserInfo(String accessToken) {
        WebClient webClient = WebClient.create(serverUrl + "/realms/" + userRealm + "/protocol/openid-connect/userinfo");
        return webClient.get()
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(ResponseAuthKeycloak.class)
                .block();
    }

    public List<UserRepresentation> searchUserByUsername(String userName) {
        UsersResource usersResource = keycloakAdminService.getInstance().realm(userRealm).users();

        return usersResource.search(userName, true);
    }


    private CredentialRepresentation createPasswordCredential(String password) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        return credential;
    }

    public void changeUserPassword(String userId, String newPassword) {
        try {
            UserResource userResource = keycloakAdminService.getInstance().realm(userRealm).users().get(userId);
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(newPassword);
            credential.setTemporary(false);
            userResource.resetPassword(credential);
        } catch (NotFoundException e) {
            throw new ResourceNotFoundException("User with id " + userId + " not found");
        }
    }

    private UserRepresentation createUserRepresentation(UserDTO userDTO) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.firstname() + " " + userDTO.lastName());
        user.setFirstName(userDTO.firstname());
        user.setLastName(userDTO.lastName());
        user.setEmail(userDTO.email());
        user.setCredentials(Collections.singletonList(createPasswordCredential(userDTO.password())));
        user.setEnabled(true);
        return user;
    }

    private void handleCreateUserResponse(Response response, String email, String password) {
        switch (response.getStatus()) {
            case 201 -> logger.info("Successfully created user with email: {}", email);
            case 409 -> throw new UserAlreadyExistsException("User already exists with email: " + email);
            default -> throw new UserCreationException("Failed to create user with email: " + email + ". Status: " + response.getStatus());
        }
    }



    public UserAccountInfo getAccountData(String email){

        return new UserAccountInfo(
                this.orderService.getOrdersByUserEmail(email)
        );


    }

    public UserResponseDTO login(UserDTO userDTO) {
        // Step 1: Get access token
        KeycloakTokenResponse responseToken = this.keycloakTokenService.fetchAccessToken(userDTO.email(), userDTO.password());

        // Step 2: Extract access token
        String accessToken = responseToken.access_token();

        String refreshToken = responseToken.refresh_token();

        // Step 3: Send request and get user info
        ResponseAuthKeycloak userResponse = getUserInfo(accessToken);

        // Step 4: Return response DTO
        return new UserResponseDTO(accessToken, refreshToken, userResponse.given_name(), userResponse.family_name(), userResponse.email());
    }

    public void logout(String accessToken, String refreshToken) {
        WebClient webClient = WebClient.create(serverUrl + "/realms/" + userRealm + "/protocol/openid-connect/logout");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("refresh_token", refreshToken);

        webClient.post()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(map))
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return Mono.empty();
                    } else {
                        return response.createException().flatMap(Mono::error);
                    }
                })
                .block();
    }

    public UserRepresentation getUserByEmail(String email) {
        // Получаем экземпляр Keycloak
        Keycloak keycloak = keycloakAdminService.getInstance();

        // find users by email
        List<UserRepresentation> users = keycloak.realm(userRealm)
                .users()
                .search(email, 0, 1);

        // if users is empty, then throw NotFoundException
        if (users.isEmpty()) {
            throw new NotFoundException("User with email " + email + " not found");
        }


        return users.get(0);
    }

    public void restorePassword(String email) {
        UserRepresentation user = getUserByEmail(email);
        if (user == null) {
            throw new NotFoundException("User with email " + email + " not found");
        }

        PasswordResetToken passwordResetToken = createPasswordResetTokenForUser(user);
        sendEmailWithTokenRestorePassword( passwordResetToken, email);

    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken == null) {
            throw new NotFoundException("Token not found");
        }

        if (passwordResetToken.getExpiryDate().before(new Date())) {
            throw new TokenExpiredException("Token expired");
        }

        changeUserPassword(passwordResetToken.getUserId(), newPassword);
        passwordResetTokenRepository.delete(passwordResetToken);
    }

    private void sendEmailWithTokenRestorePassword(PasswordResetToken passwordResetToken, String to) {

        String subject = "Password Reset Request";
        String text = "To reset your password, click the following link: "
                + resetPasswordUrl + "?token=" + passwordResetToken.getToken();

        emailService.sendSimpleMessage(to, subject, text);
    }

    private PasswordResetToken createPasswordResetTokenForUser(UserRepresentation user) {

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(UUID.randomUUID().toString());
        passwordResetToken.setUserId(user.getId());
        passwordResetToken.setExpiryDate(new Date(System.currentTimeMillis() + 3600000));


        return passwordResetTokenRepository.save(passwordResetToken);
    }



}