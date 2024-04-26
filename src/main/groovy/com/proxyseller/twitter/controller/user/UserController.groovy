package com.proxyseller.twitter.controller.user

import com.proxyseller.twitter.dto.user.UserDto
import com.proxyseller.twitter.dto.user.UserUpdateRequestDto
import com.proxyseller.twitter.service.user.UserService
import groovy.util.logging.Slf4j
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
class UserController {
    private final UserService userService

    UserController(UserService userService) {
        this.userService = userService
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    // TODO: Change to ROLE_ADMIN after migration
    UserDto getUserById(@PathVariable(name = "id") String id) {
        log.info("UserController: Getting user by id: ${id}")
        return userService.getUserById(id)
    }

    @GetMapping("/username")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    UserDto getUserByUsername(@RequestParam(name = "username") String username) {
        log.info("UserController: Getting user by username: ${username}")
        return userService.getUserByUsername(username)
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    // TODO: Change to ROLE_ADMIN after migration
    List<UserDto> getAllUsers() {
        log.info("UserController: Getting all users")
        return userService.getAllUsers()
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    UserDto updateUser(@PathVariable(name = "id") String id, @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        log.info("UserController: Updating user with id: ${id}, request: ${userUpdateRequestDto}")
        return userService.updateUser(id, userUpdateRequestDto)
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    void deleteUserById(@PathVariable(name = "id") String id) {
        log.info("UserController: Deleting user by id: ${id}")
        userService.deleteUserById(id)
    }

    @GetMapping("/followers")
    @PreAuthorize("hasRole('ROLE_USER')")
    List<UserDto> getFollowers() {
        log.info("UserController: Getting followers for current user")
        return userService.getFollowers()
    }

    @GetMapping("/following")
    @PreAuthorize("hasRole('ROLE_USER')")
    List<UserDto> getFollowing() {
        log.info("UserController: Getting following for current user")
        return userService.getFollowing()
    }

    @PostMapping("/follow/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    void followUser(@PathVariable(name = "id") String id) {
        log.info("UserController: Following user with id: ${id}")
        userService.followUser(id)
    }

    @PostMapping("/unfollow/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    void unfollowUser(@PathVariable(name = "id") String id) {
        log.info("UserController: Unfollowing user with id: ${id}")
        userService.unfollowUser(id)
    }
}
