package com.secretroomwebsite.keycloack;

public record UserDTO(
        String userName,
        String emailId,
        String password,
        String firstname,
        String lastName
) { }
