package com.secretroomwebsite.user;

import com.secretroomwebsite.authentication.KeycloakAdminService;
import com.secretroomwebsite.authentication.KeycloakTokenService;
import com.secretroomwebsite.authentication.PasswordResetTokenRepository;
import com.secretroomwebsite.emailClient.EmailService;
import com.secretroomwebsite.order.OrderService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private KeycloakAdminService keycloakAdminService;

    @Mock
    private KeycloakTokenService keycloakTokenService;

    @Mock
    private OrderService orderService;

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UserService userService;

    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        userDTO = new UserDTO("test@test.com", "password", "John", "Doe");
    }

    @Test
    public void testCreateUserWithValidData() {
        Keycloak keycloak = mock(Keycloak.class);
        RealmResource realmResource = mock(RealmResource.class);
        UsersResource usersResource = mock(UsersResource.class);

        when(keycloakAdminService.getInstance()).thenReturn(keycloak);
        when(keycloak.realm(anyString())).thenReturn(realmResource);
        when(realmResource.users()).thenReturn(usersResource);
        when(usersResource.create(any(UserRepresentation.class))).thenReturn(Response.status(Response.Status.CREATED).build());

        userService.createUser(userDTO);

        verify(keycloakAdminService, times(1)).getInstance();
        verify(keycloakTokenService, times(1)).fetchAccessToken(anyString(), anyString());
    }

    @Test
    public void testSearchUserByUsernameWithValidUsername() {
        Keycloak keycloak = mock(Keycloak.class);
        UsersResource usersResource = mock(UsersResource.class);
        when(keycloakAdminService.getInstance()).thenReturn(keycloak);
        when(keycloak.realm(anyString()).users()).thenReturn(usersResource);
        when(usersResource.search(anyString(), anyBoolean())).thenReturn(Collections.singletonList(new UserRepresentation()));

        userService.searchUserByUsername("John");

        verify(keycloakAdminService, times(1)).getInstance();
    }

    @Test
    public void testChangeUserPasswordWithValidData() {
        Keycloak keycloak = mock(Keycloak.class);
        when(keycloakAdminService.getInstance()).thenReturn(keycloak);

        userService.changeUserPassword("1", "newPassword");

        verify(keycloakAdminService, times(1)).getInstance();
    }

    @Test
    public void testGetAccountDataWithValidEmail() {
        userService.getAccountData("test@test.com");

        verify(orderService, times(1)).getOrdersByUserEmail(anyString());
    }

    @Test
    public void testLoginWithValidData() {
        Keycloak keycloak = mock(Keycloak.class);
        when(keycloakAdminService.getInstance()).thenReturn(keycloak);

        userService.login(userDTO);

        verify(keycloakTokenService, times(1)).fetchAccessToken(anyString(), anyString());
    }
}