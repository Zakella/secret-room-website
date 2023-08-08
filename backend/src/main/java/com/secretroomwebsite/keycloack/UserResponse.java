package com.secretroomwebsite.keycloack;

public record UserResponse(
    String accessToken,
    String tokenType,
    long expiresIn,
    String firstName,
    String lastName,
    String email
) {}
