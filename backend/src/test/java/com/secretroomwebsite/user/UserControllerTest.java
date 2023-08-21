package com.secretroomwebsite.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

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
        doNothing().when(userService).logout(anyString());

        ResponseEntity<Void> response = userController.logout(accessToken);

        verify(userService, times(1)).logout("validAccessToken");
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testLogoutWhenGivenInvalidAccessTokenThenExceptionIsThrown() {
        String accessToken = "Bearer invalidAccessToken";
        doThrow(new IllegalArgumentException()).when(userService).logout(anyString());

        try {
            userController.logout(accessToken);
        } catch (Exception e) {
            verify(userService, times(1)).logout("invalidAccessToken");
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }
}