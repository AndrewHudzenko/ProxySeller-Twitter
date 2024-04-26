package com.proxyseller.twitter.dto.user

class UserRegistrationRequestDto {
    private String firstName
    private String lastName
    private String username
    private String password
    private String email
    private Integer age

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

    String getPassword() {
        return password
    }

    void setPassword(String password) {
        this.password = password
    }

    String getEmail() {
        return email
    }

    void setEmail(String email) {
        this.email = email
    }

    Integer getAge() {
        return age
    }

    void setAge(Integer age) {
        this.age = age
    }

    UserRegistrationRequestDto() {
    }

    UserRegistrationRequestDto(String firstName, String lastName, String username, String password, String email, Integer age) {
        this.firstName = firstName
        this.lastName = lastName
        this.username = username
        this.password = password
        this.email = email
        this.age = age
    }
}
