package com.proxyseller.twitter.service.user.impl

import com.proxyseller.twitter.dto.user.UserDto
import com.proxyseller.twitter.dto.user.UserRegistrationRequestDto
import com.proxyseller.twitter.dto.user.UserUpdateRequestDto
import com.proxyseller.twitter.exception.EntityNotFoundException
import com.proxyseller.twitter.exception.RegistrationException
import com.proxyseller.twitter.mapper.user.UserMapper
import com.proxyseller.twitter.model.user.User
import com.proxyseller.twitter.repository.user.UserRepository
import com.proxyseller.twitter.security.SecurityUtil
import com.proxyseller.twitter.service.user.UserService
import groovy.util.logging.Slf4j
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.util.stream.Collectors

@Slf4j
@Service
class UserServiceImpl implements UserService {

    private final UserRepository userRepository
    private final UserMapper userMapper
    private final PasswordEncoder passwordEncoder;

    UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository
        this.userMapper = userMapper
        this.passwordEncoder = passwordEncoder
    }

    @Override
    UserDto registerUser(UserRegistrationRequestDto userRegistrationRequestDto) {
        // TODO: Fix Groovy bug related to mapper
        if (userRepository.findByEmailAndIsDeletedFalse(userRegistrationRequestDto.getEmail()).isPresent()) {
            log.warn("UserServiceImpl: User with such email already registered!")
            throw new RegistrationException("User with such email already registered!");
        }
        User user = new User();
        user.setFirstName(userRegistrationRequestDto.getFirstName());
        user.setLastName(userRegistrationRequestDto.getLastName());
        user.setUsername(userRegistrationRequestDto.getUsername());
        user.setEmail(userRegistrationRequestDto.getEmail());
        user.setAge(userRegistrationRequestDto.getAge());
        user.setPassword(passwordEncoder.encode(userRegistrationRequestDto.getPassword()));

        log.info("UserServiceImpl: Registering new user: ${user}")
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    UserDto getUserById(String id) {
        log.info("UserServiceImpl: Getting user by id: ${id}")
        return userMapper.toDto(userRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found!")))
    }

    @Override
    UserDto getUserByUsername(String username) {
        log.info("UserServiceImpl: Getting user by username: ${username}")
        return userMapper.toDto(userRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found!")))
    }

    @Override
    List<UserDto> getAllUsers() {
        log.info("UserServiceImpl: Getting all users")
        return userRepository.findAllByIsDeletedFalse().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList())
    }

    @Override
    UserDto updateUser(String id, UserUpdateRequestDto userUpdateRequestDto) {
        User user = userRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"))

        User updatedUser = userMapper.toModel(userUpdateRequestDto)
        updatedUser.setId(user.getId())
        updatedUser.setFollowers(user.getFollowers())
        updatedUser.setFollowing(user.getFollowing())

        log.info("UserServiceImpl: Updating user with id: ${id}, request: ${userUpdateRequestDto}")
        return userMapper.toDto(userRepository.save(updatedUser))
    }

    @Override
    void deleteUserById(String id) {
        User user = userRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"))

        user.setIsDeleted(true)

        log.info("UserServiceImpl: Deleting user with id: ${id}")
        userRepository.save(user)
    }

    @Override
    List<UserDto> getFollowers() {
        String currentUserId = SecurityUtil.getCurrentUserId()
        User user = userRepository.findByIdAndIsDeletedFalse(currentUserId)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"))

        log.info("UserServiceImpl: Getting followers for current user")
        Set<String> followersIds = user.getFollowers()
        return userRepository.findAllByIdInAndIsDeletedFalse(followersIds).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList())
    }

    @Override
    List<UserDto> getFollowing() {
        String currentUserId = SecurityUtil.getCurrentUserId()
        User user = userRepository.findByIdAndIsDeletedFalse(currentUserId)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"))

        log.info("UserServiceImpl: Getting following for current user")
        Set<String> followingIds = user.getFollowing()
        return userRepository.findAllByIdInAndIsDeletedFalse(followingIds).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList())
    }

    @Override
//    @Transactional
    void followUser(String id) {
        String currentUserId = SecurityUtil.getCurrentUserId()
        User currentUser = userRepository.findByIdAndIsDeletedFalse(currentUserId)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"))

        User followingUser = userRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"))

        if (userRepository.findByIdAndIsDeletedFalse(id).isEmpty()) {
            log.warn("UserServiceImpl: User with id: ${id} not found!")
            throw new EntityNotFoundException("User not found!")
        }

        if (currentUserId.equals(id)) {
            log.warn("UserServiceImpl: User can't follow himself!")
            throw new IllegalArgumentException("User can't follow himself!")
        }

        currentUser.followUser(id)
        followingUser.addFollower(currentUserId)

        log.info("UserServiceImpl: Following user with id: ${id}")
        userRepository.save(currentUser)
        userRepository.save(followingUser)
    }

    @Override
//    @Transactional
    void unfollowUser(String string) {
        String currentUserId = SecurityUtil.getCurrentUserId()
        User currentUser = userRepository.findByIdAndIsDeletedFalse(currentUserId)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"))

        User followingUser = userRepository.findByIdAndIsDeletedFalse(string)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"))

        if (userRepository.findByIdAndIsDeletedFalse(string).isEmpty()) {
            log.warn("UserServiceImpl: User with id: ${string} not found!")
            throw new EntityNotFoundException("User not found!")
        }

        if (currentUserId.equals(string)) {
            log.warn("UserServiceImpl: User can't unfollow yourself!")
            throw new IllegalArgumentException("User can't unfollow yourself!")
        }

        currentUser.unfollowUser(string)
        followingUser.removeFollower(currentUserId)

        log.info("UserServiceImpl: Unfollowing user with id: ${string}")
        userRepository.save(currentUser)
        userRepository.save(followingUser)
    }
}
