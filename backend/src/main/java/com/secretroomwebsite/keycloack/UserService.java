package com.secretroomwebsite.keycloack;

import com.secretroomwebsite.exception.UserAlreadyExistsException;
import com.secretroomwebsite.exception.UserCreationException;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final KeycloakAdminService keycloakAdminService;

    private final KeycloakTokenService keycloakTokenService;

    @Value("${keycloak.user-realm}")
    private String userRealm;

    @Autowired
    public UserService(KeycloakAdminService keycloakAdminService, KeycloakTokenService keycloakTokenService) {
        this.keycloakAdminService = keycloakAdminService;
        this.keycloakTokenService = keycloakTokenService;
    }

    public void createUser(UserDTO userDTO)  {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(userDTO.password());

        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.userName());
        user.setFirstName(userDTO.firstname());
        user.setLastName(userDTO.lastName());
        user.setEmail(userDTO.emailId());
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);

        UsersResource usersResource = keycloakAdminService.getInstance().realm(userRealm).users();
        Response response = usersResource.create(user);

        switch (response.getStatus()) {
            case 201 -> {
                logger.info("Successfully created user with username: " + userDTO.userName());
                KeycloakTokenResponse responseToken = this.keycloakTokenService.fetchAccessToken(userDTO.emailId(), userDTO.password());
                logger.info("Token: " + responseToken.access_token());
            }
            case 409 ->
                    throw new UserAlreadyExistsException("User already exists with username: " + userDTO.userName());
            default ->
                    throw new UserCreationException("Failed to create user with username: " + userDTO.userName() + ". Status: " + response.getStatus());
        }
    }


    public List<UserRepresentation> getUser(String userName){
        UsersResource usersResource  = keycloakAdminService.getInstance().realm(userRealm).users();
        return usersResource.search(userName, true);

    }

//    public UserResponse sendUserInfo (KeycloakTokenResponse tokenInfo, String userName){
////        List<UserRepresentation> =
//
////        return new UserResponse(
////                tokenInfo.access_token(),
////                tokenInfo.token_type(),
////                tokenInfo.expires_in()
////
////
////        );
//
//    }


}

