package com.secretroomwebsite.user;

public record UserResponseDTO(
    String accessToken,
    String givenName,
    String familyName,
    String email
) {}