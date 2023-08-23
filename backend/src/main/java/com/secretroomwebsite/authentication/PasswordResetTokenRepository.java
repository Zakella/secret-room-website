package com.secretroomwebsite.authentication;

import org.springframework.data.jpa.repository.JpaRepository;
import com.secretroomwebsite.authentication.PasswordResetToken;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
}