package com.proxyseller.twitter.service.util

import com.proxyseller.twitter.dto.user.UserDto
import com.proxyseller.twitter.dto.user.UserRegistrationRequestDto
import com.proxyseller.twitter.dto.user.UserUpdateRequestDto

class DtoGeneratorUtil {

    static UserRegistrationRequestDto generateUserRegistrationRequestDto() {
        return new UserRegistrationRequestDto(
                firstName: "John",
                lastName: "Doe",
                username: "johndoe",
                password: "1234",
                email: "johndoe@gmail.com",
                age: 25
        )
    }

    static UserDto generateUserDto() {
        return new UserDto(
                id: "1",
                username: "johndoe",
                email: "johndoe@gmail.com"
        )
    }

    static UserUpdateRequestDto generateUserUpdateRequestDto() {
        return new UserUpdateRequestDto(
                firstName: "David",
                lastName: "Queen",
                username: "davidqueen",
                email: "davidqueen@gmail.com",
                password: "encodedPassword",
                age: 22
        )
    }

}
