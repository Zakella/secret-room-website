package com.secretroomwebsite.authentication;

public record PasswordChangeRequest (
    String userId,
    String newPassword
){}
