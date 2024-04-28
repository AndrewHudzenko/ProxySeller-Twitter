package com.proxyseller.twitter.service.util

import com.proxyseller.twitter.model.user.User

class ModelGeneratorUtil {

    static User generateUser() {
        return new User(
                id: "1",
                firstName: "John",
                lastName: "Doe",
                username: "johndoe",
                password: "encodedPassword",
                email: "johndoe@gmail.com",
                age: 25,
                followers: ["2", "3"],
                following: ["4", "5"],
                isDeleted: false
        )
    }
}
