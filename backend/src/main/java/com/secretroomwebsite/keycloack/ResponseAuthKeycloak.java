package com.secretroomwebsite.keycloack;

public record ResponseAuthKeycloak(
    String sub,
    boolean email_verified,
    String name,
    String preferred_username,
    String given_name,
    String family_name,
    String email
) {}