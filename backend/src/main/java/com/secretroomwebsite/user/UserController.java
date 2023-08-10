package com.secretroomwebsite.user;

import com.secretroomwebsite.order.Order;
import com.secretroomwebsite.order.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
        UserResponseDTO userResponseDTO = userService.createUser(userDTO);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody UserDTO userDTO) {
        UserResponseDTO userResponseDTO = userService.login(userDTO);
        return ResponseEntity.ok(userResponseDTO);
    }


    @PostMapping("/accountInfo")
    public ResponseEntity<UserAccountInfo> getAccountInformation(@RequestBody Map<String, String> body) {
        String userEmail = body.get("email");
        if (userEmail == null || userEmail.isEmpty()) {
            throw new IllegalArgumentException("Email must not be null or empty");
        }
        return new ResponseEntity<>(userService.getAccountData(userEmail), HttpStatus.OK);
    }


}