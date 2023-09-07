package com.secretroomwebsite.user;

import com.secretroomwebsite.order.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserDTO userDTO) {
        UserCredentials userCredentials = userService.createUser(userDTO);
        return getUserResponseDTOResponseEntity(userCredentials);
    }



    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody UserDTO userDTO) {
        UserCredentials userCredentials = userService.login(userDTO);
        return getUserResponseDTOResponseEntity(userCredentials);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String accessToken, @RequestBody Map<String, String> body) {
        String refreshToken = body.get("refresh_token");
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new IllegalArgumentException("Refresh token must not be null or empty");
        }
        userService.logout(accessToken.replace("Bearer ", ""), refreshToken);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/accountInfo")
    public ResponseEntity<UserAccountInfo> getAccountInformation(@RequestBody Map<String, String> body) {
        String userEmail = body.get("email");
        if (userEmail == null || userEmail.isEmpty()) {
            throw new IllegalArgumentException("Email must not be null or empty");
        }
        return new ResponseEntity<>(userService.getAccountData(userEmail), HttpStatus.OK);
    }

    @GetMapping("/restore-password")
    public ResponseEntity<Void> restorePassword(@RequestParam String email, @RequestParam String lang) {
        userService.restorePassword(email, lang);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        String newPassword = body.get("newPassword");
        if (token == null || token.isEmpty() || newPassword == null || newPassword.isEmpty()) {
            throw new IllegalArgumentException("Token and new password must not be null or empty");
        }
        userService.resetPassword(token, newPassword);
        return ResponseEntity.ok().build();
    }

    @NotNull
    private ResponseEntity<UserResponseDTO> getUserResponseDTOResponseEntity(UserCredentials userCredentials) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Set-Cookie", createCookieHeader("accessToken", userCredentials.accessToken()));
        responseHeaders.add("Set-Cookie", createCookieHeader("refreshToken", userCredentials.refreshToken()));
        return ResponseEntity.ok().headers(responseHeaders).body(userCredentials.userResponseDTO());
    }

    private String createCookieHeader(String name, String value) {
        return name + "=" + value + "; Path=/; SameSite=None; HttpOnly";
//        return name + "=" + value + "; Path=/; SameSite=" + "Lax" + "; Secure; HttpOnly";
    }

    @GetMapping("/tokenIsValid")
    public ResponseEntity<Boolean> tokenIsValid(HttpServletRequest request) {
        boolean isValid = userService.hasValidAccessToken(request);
        return ResponseEntity.ok(isValid);
    }

}