package com.proxyseller.twitter.dto.user

class UserUpdateRequestDto {
    private String firstName
    private String lastName
    private String username
    private String email
    private String password
    private Integer age

    UserUpdateRequestDto() {
    }

    UserUpdateRequestDto(String firstName, String lastName, String username, String email, String password, Integer age) {
        this.firstName = firstName
        this.lastName = lastName
        this.username = username
        this.email = email
        this.password = password
        this.age = age
    }

    String getFirstName() {
        return firstName
    }

    void setFirstName(String firstName) {
        this.firstName = firstName
    }

    String getLastName() {
        return lastName
    }

    void setLastName(String lastName) {
        this.lastName = lastName
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

    String getPassword() {
        return password
    }

    void setPassword(String password) {
        this.password = password
    }

    Integer getAge() {
        return age
    }

    void setAge(Integer age) {
        this.age = age
    }
}