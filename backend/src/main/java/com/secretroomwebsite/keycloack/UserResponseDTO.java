package com.secretroomwebsite.keycloack;

public record UserResponseDTO(
    String accessToken,
    String givenName,
    String familyName,
    String email
) {}