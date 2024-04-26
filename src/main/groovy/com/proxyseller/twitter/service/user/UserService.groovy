package com.proxyseller.twitter.service.user

import com.proxyseller.twitter.dto.user.UserDto
import com.proxyseller.twitter.dto.user.UserRegistrationRequestDto
import com.proxyseller.twitter.dto.user.UserUpdateRequestDto

/**
 * Interface to define the methods for the user service
 */
interface UserService {

    /**
     * Method to register a new user
     * @param userRegistrationRequestDto
     * @return UserDto
     */
    UserDto registerUser(UserRegistrationRequestDto userRegistrationRequestDto);

    /**
     * Method to get a user by id
     * @param id
     * @return UserDto
     */
    UserDto getUserById(String id);

    /**
     * Method to get all users
     * @return List<UserDto>
     */
    List<UserDto> getAllUsers();

    /**
     * Method to update a user
     * @param id
     * @param userUpdateRequestDto
     * @return UserDto
     */
    UserDto updateUser(String id, UserUpdateRequestDto userUpdateRequestDto);

    /**
     * Method to get a user by username
     * @param username
     * @return UserDto
     */
    UserDto getUserByUsername(String username);

    /**
     * Method to delete a user by id
     * @param id
     */
    void deleteUserById(String id);

    /**
     * Method to get the followers of current user
     * @return List<UserDto>
     */
    List<UserDto> getFollowers()

    /**
     * Method to get the following of current user
     * @return List<UserDto>
     */
    List<UserDto> getFollowing()

    /**
     * Method to follow a user
     * @param id
     */
    void followUser(String id)

    /**
     * Method to unfollow a user
     * @param id
     */
    void unfollowUser(String string)
}