package com.secretroomwebsite.keycloack;

public record UserDTO(
        String email,
        String password,
        String firstname,
        String lastName
) { }
