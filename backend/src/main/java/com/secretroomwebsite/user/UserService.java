package com.secretroomwebsite.user;

import com.secretroomwebsite.exception.UserAlreadyExistsException;
import com.secretroomwebsite.exception.UserCreationException;
import com.secretroomwebsite.keycloack.KeycloakAdminService;
import com.secretroomwebsite.keycloack.KeycloakTokenResponse;
import com.secretroomwebsite.keycloack.KeycloakTokenService;
import com.secretroomwebsite.keycloack.ResponseAuthKeycloak;
import com.secretroomwebsite.order.OrderService;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;

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



    @Autowired
    public UserService(KeycloakAdminService keycloakAdminService,
                       KeycloakTokenService keycloakTokenService,
                       OrderService orderService) {

        this.keycloakAdminService = keycloakAdminService;
        this.keycloakTokenService = keycloakTokenService;
        this.orderService = orderService;
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

        // Step 4: Send request and get user info
        ResponseAuthKeycloak userResponse = getUserInfo(accessToken);

        // Step 5: Return response DTO
        return new UserResponseDTO(accessToken, userResponse.given_name(), userResponse.family_name(), userResponse.email());

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
            case 201 -> {
                logger.info("Successfully created user with email: {}", email);
                KeycloakTokenResponse responseToken = this.keycloakTokenService.fetchAccessToken(email, password);
                logger.info("Token: {}", responseToken.access_token());
            }
            case 409 -> throw new UserAlreadyExistsException("User already exists with email: " + email);
            default ->
                    throw new UserCreationException("Failed to create user with email: " + email + ". Status: " + response.getStatus());
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

        // Step 3: Send request and get user info
        ResponseAuthKeycloak userResponse = getUserInfo(accessToken);

        // Step 4: Return response DTO
        return new UserResponseDTO(accessToken, userResponse.given_name(), userResponse.family_name(), userResponse.email());
    }
}