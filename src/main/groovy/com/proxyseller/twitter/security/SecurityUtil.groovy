package com.proxyseller.twitter.security

import com.proxyseller.twitter.model.user.User
import com.proxyseller.twitter.service.user.UserService
import lombok.extern.slf4j.Slf4j
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Slf4j
@Component
class SecurityUtil {
    private final UserService userService

    SecurityUtil(UserService userService) {
        this.userService = userService
    }

    static String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication()
        User user = (User) authentication.getPrincipal()
        return user.getId()
    }

    static String encodePassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder()
        return passwordEncoder.encode(password)
    }
}
