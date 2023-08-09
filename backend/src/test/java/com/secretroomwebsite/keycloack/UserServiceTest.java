package com.secretroomwebsite.keycloack;

import com.secretroomwebsite.exception.UserAlreadyExistsException;
import com.secretroomwebsite.exception.UserCreationException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



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

    private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);


    @Test
    @DisplayName("Should return an empty list when no users match the provided username")
    void searchUserByUsernameWhenNoUsersMatch() {
        String username = "john.doe";
        UsersResource usersResource = mock(UsersResource.class);
        when(keycloakAdminService.getInstance().realm(userRealm).users()).thenReturn(usersResource);
        when(usersResource.search(username, true)).thenReturn(Collections.emptyList());

        List<UserRepresentation> result = userService.searchUserByUsername(username);

        assertTrue(result.isEmpty());
        verify(keycloakAdminService, times(1)).getInstance();
        verify(usersResource, times(1)).search(username, true);
    }

    @Test
    @DisplayName("Should throw UserAlreadyExistsException when the email is already registered")
    void createUserWhenEmailIsAlreadyRegisteredThenThrowUserAlreadyExistsException() {
        UserDTO userDTO = new UserDTO(
                "john.doe",
                "john.doe@example.com",
                "password",
                "John",
                "Doe"
        );

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail(userDTO.emailId());

        UsersResource usersResource = mock(UsersResource.class);
        when(keycloakAdminService.getInstance().realm(userRealm).users()).thenReturn(usersResource);
        when(usersResource.create(any(UserRepresentation.class))).thenReturn(Response.status(409).build());

        // Act and Assert
        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.createUser(userDTO);
        });

        verify(usersResource, times(1)).create(any(UserRepresentation.class));
    }

    @Test
    @DisplayName("Should create a new user successfully when the email is not already registered")
    void createUserWhenEmailIsNotRegistered() {
        UserDTO userDTO = new UserDTO(
                "john.doe",
                "john.doe@example.com",
                "password123",
                "John",
                "Doe"
        );

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(userDTO.userName());
        userRepresentation.setFirstName(userDTO.firstname());
        userRepresentation.setLastName(userDTO.lastName());
        userRepresentation.setEmail(userDTO.emailId());
        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(userDTO));
        userRepresentation.setEnabled(true);

        UsersResource usersResource = mock(UsersResource.class);
        Response response = mock(Response.class);
        when(keycloakAdminService.getInstance().realm(userRealm).users()).thenReturn(usersResource);
        when(usersResource.create(any(UserRepresentation.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(201);

        userService.createUser(userDTO);

        verify(usersResource, times(1)).create(userRepresentation);
        verify(keycloakTokenService, times(1)).fetchAccessToken(userDTO.emailId(), userDTO.password());
    }

    @Test
    @DisplayName("Should throw UserCreationException when the user creation fails for reasons other than duplicate email")
    void createUserWhenCreationFailsThenThrowUserCreationException() {
        // Mock the necessary dependencies
        UsersResource usersResource = mock(UsersResource.class);
        Response response = mock(Response.class);
        when(keycloakAdminService.getInstance()).thenReturn(mock(Keycloak.class));
        when(keycloakAdminService.getInstance().realm(anyString()).users()).thenReturn(usersResource);
        when(usersResource.create(any(UserRepresentation.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(500);

        // Create a userDTO
        UserDTO userDTO = new UserDTO(
                "john.doe",
                "john.doe@example.com",
                "password123",
                "John",
                "Doe"
        );

        // Assert that UserCreationException is thrown
        assertThrows(UserCreationException.class, () -> userService.createUser(userDTO));
    }


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