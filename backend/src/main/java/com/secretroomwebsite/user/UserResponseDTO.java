package com.secretroomwebsite.user;

public record UserResponseDTO(
        String accessToken,

        String refreshToken,
        String givenName,
        String familyName,
        String email
) {}