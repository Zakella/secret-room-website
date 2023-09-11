package com.secretroomwebsite.user;
import com.secretroomwebsite.order.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserDTO userDTO) {
        UserResponseDTO userResponseDTO = userService.createUser(userDTO);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody UserDTO userDTO) {
        UserResponseDTO userResponseDTO = userService.login(userDTO);
        return ResponseEntity.ok(userResponseDTO);
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


    @PostMapping("/`accountInfo")
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

}