package com.secretroomwebsite.user;

public record UserCredentials(
        String accessToken,
        String refreshToken,

        UserResponseDTO userResponseDTO) {
}
