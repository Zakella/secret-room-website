package com.secretroomwebsite.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogoutWhenGivenValidAccessTokenThenUserServiceLogoutIsCalled() {
        String accessToken = "Bearer validAccessToken";
        String refreshToken = "validRefreshToken";
        Map<String, String> body = new HashMap<>();
        body.put("refresh_token", refreshToken);
        doNothing().when(userService).logout(anyString(), anyString());

        ResponseEntity<Void> response = userController.logout(accessToken, body);

        verify(userService, times(1)).logout("validAccessToken", "validRefreshToken");
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testLogoutWhenGivenInvalidAccessTokenThenExceptionIsThrown() {
        String accessToken = "Bearer invalidAccessToken";
        String refreshToken = "invalidRefreshToken";
        Map<String, String> body = new HashMap<>();
        body.put("refresh_token", refreshToken);
        doThrow(new IllegalArgumentException()).when(userService).logout(anyString(), anyString());

        try {
            userController.logout(accessToken, body);
        } catch (Exception e) {
            verify(userService, times(1)).logout("invalidAccessToken", "invalidRefreshToken");
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }
}