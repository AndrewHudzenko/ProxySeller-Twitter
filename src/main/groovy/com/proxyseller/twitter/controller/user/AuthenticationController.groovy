package com.proxyseller.twitter.controller.user

import com.proxyseller.twitter.dto.user.UserDto
import com.proxyseller.twitter.dto.user.UserLoginRequestDto
import com.proxyseller.twitter.dto.user.UserLoginResponseDto
import com.proxyseller.twitter.dto.user.UserRegistrationRequestDto
import com.proxyseller.twitter.security.AuthenticationService
import com.proxyseller.twitter.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    AuthenticationController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService
        this.authenticationService = authenticationService
    }

    @PostMapping("/registration")
    UserDto registerUser(@RequestBody UserRegistrationRequestDto userRegistrationRequestDto) {
        return userService.registerUser(userRegistrationRequestDto)
    }

    @PostMapping("/login")
    UserLoginResponseDto login(@RequestBody UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }

}
