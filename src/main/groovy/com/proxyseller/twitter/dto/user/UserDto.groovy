package com.proxyseller.twitter.dto.user

class UserDto {
    private String id
    private String username
    private String email

    UserDto() {
    }

    UserDto(String id, String username, String email) {
        this.id = id
        this.username = username
        this.email = email
    }

    String getId() {
        return id
    }

    void setId(String id) {
        this.id = id
    }

    String getUsername() {
        return username
    }

    void setUsername(String username) {
        this.username = username
    }

    String getEmail() {
        return email
    }

    void setEmail(String email) {
        this.email = email
    }
}
