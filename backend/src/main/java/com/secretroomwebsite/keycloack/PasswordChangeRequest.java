package com.secretroomwebsite.keycloack;

public record PasswordChangeRequest (
    String userId,
    String newPassword
){}
