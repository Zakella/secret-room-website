package com.secretroomwebsite.user;

public record UserDTO(
        String email,
        String password,
        String firstname,
        String lastName
) { }
