package com.secretroomwebsite.keycloack;

import com.secretroomwebsite.exception.UserCreationException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private KeycloakAdminService keycloakAdminService;

    @Mock
    private KeycloakTokenService keycloakTokenService;

    @InjectMocks
    private UserService userService;

    @Value("${keycloak.user-realm}")
    private String userRealm;

    @Test
    @DisplayName("Should throw UserCreationException when the user creation fails")
    void createUserWhenUserCreationFailsThenThrowException() {
        UserDTO userDTO = new UserDTO(
                "john.doe",
                "john.doe@example.com",
                "password123",
                "John",
                "Doe"
        );

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

        UsersResource usersResource = mock(UsersResource.class);
        when(keycloakAdminService.getInstance().realm(userRealm).users()).thenReturn(usersResource);

        Response response = mock(Response.class);
        when(usersResource.create(user)).thenReturn(response);

        when(response.getStatus()).thenReturn(500);

        assertThrows(UserCreationException.class, () -> userService.createUser(userDTO));


    }

    @Test
    @DisplayName("Should create the user successfully and return access token when the username is not taken")
    void createUserWhenUsernameIsNotTaken() {
        UserDTO userDTO = new UserDTO(
                "john.doe",
                "john.doe@example.com",
                "password",
                "John",
                "Doe"
        );

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

        UsersResource usersResource = mock(UsersResource.class);
        when(keycloakAdminService.getInstance().realm(userRealm).users()).thenReturn(usersResource);

        Response response = mock(Response.class);
        when(usersResource.create(user)).thenReturn(response);
        when(response.getStatus()).thenReturn(201);

        KeycloakTokenResponse responseToken = new KeycloakTokenResponse(
                "access_token",
                3600,
                3600,
                "refresh_token",
                "token_type",
                0,
                "session_state",
                "scope"
        );
        when(keycloakTokenService.fetchAccessToken(userDTO.emailId(), userDTO.password())).thenReturn(responseToken);

        userService.createUser(userDTO);

        verify(usersResource, times(1)).create(user);
        verify(keycloakTokenService, times(1)).fetchAccessToken(userDTO.emailId(), userDTO.password());
    }

}