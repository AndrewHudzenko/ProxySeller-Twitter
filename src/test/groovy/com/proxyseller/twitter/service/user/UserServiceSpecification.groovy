package com.proxyseller.twitter.service.user

import com.proxyseller.twitter.dto.user.UserDto
import com.proxyseller.twitter.dto.user.UserUpdateRequestDto
import com.proxyseller.twitter.exception.EntityNotFoundException
import com.proxyseller.twitter.mapper.user.UserMapper
import com.proxyseller.twitter.model.user.User
import com.proxyseller.twitter.repository.user.UserRepository
import com.proxyseller.twitter.security.SecurityUtil
import com.proxyseller.twitter.service.user.impl.UserServiceImpl
import com.proxyseller.twitter.service.util.DtoGeneratorUtil
import com.proxyseller.twitter.service.util.ModelGeneratorUtil
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class UserServiceSpecification extends Specification {

    def userRepository = Mock(UserRepository)
    def userMapper = Mock(UserMapper)
    def passwordEncoder = Mock(PasswordEncoder)

    def userService = new UserServiceImpl(userRepository, userMapper, passwordEncoder)

    def "register user with new email"() {
        given:
        def userRegistrationRequestDto = DtoGeneratorUtil.generateUserRegistrationRequestDto()
        def userDto = DtoGeneratorUtil.generateUserDto()
        def user = ModelGeneratorUtil.generateUser()

        userRepository.findByEmailAndIsDeletedFalse(_) >> Optional.empty()
        passwordEncoder.encode(_) >> "encodedPassword"
        userRepository.save(_) >> user
        userMapper.toDto(_) >> userDto

        when:
        def actual = userService.registerUser(userRegistrationRequestDto)

        then:
        actual.username == "johndoe"
        actual.email == "johndoe@gmail.com"
        1 * userRepository.findByEmailAndIsDeletedFalse(_) >> Optional.empty()
        1 * passwordEncoder.encode(_) >> "encodedPassword"
        1 * userRepository.save(_) >> user
        1 * userMapper.toDto(_) >> userDto
    }

    def "register user with existing email should throw exception"() {
        given:
        def userRegistrationRequestDto = DtoGeneratorUtil.generateUserRegistrationRequestDto()
        def user = ModelGeneratorUtil.generateUser()

        userRepository.findByEmailAndIsDeletedFalse(_ as String) >> Optional.of(user)

        when:
        userService.registerUser(userRegistrationRequestDto)

        then:
        thrown(RuntimeException)
        1 * userRepository.findByEmailAndIsDeletedFalse(_) >> Optional.of(user)
    }

    def "get user by id"() {
        given:
        def user = ModelGeneratorUtil.generateUser()
        def userDto = DtoGeneratorUtil.generateUserDto()

        userRepository.findByIdAndIsDeletedFalse(_) >> Optional.of(user)
        userMapper.toDto(_) >> userDto

        when:
        def actual = userService.getUserById("1")

        then:
        actual.id == "1"
        actual.username == "johndoe"
        actual.email == "johndoe@gmail.com"
        1 * userRepository.findByIdAndIsDeletedFalse(_) >> Optional.of(user)
        1 * userMapper.toDto(_) >> userDto
    }

    def "get user by id should throw exception if user not found"() {
        given:
        userRepository.findByIdAndIsDeletedFalse(_) >> Optional.empty()

        when:
        userService.getUserById("1")

        then:
        thrown(RuntimeException)
        1 * userRepository.findByIdAndIsDeletedFalse(_) >> Optional.empty()
    }

    def "get user by username"() {
        given:
        def user = ModelGeneratorUtil.generateUser()
        def userDto = DtoGeneratorUtil.generateUserDto()

        userRepository.findByUsernameAndIsDeletedFalse(_) >> Optional.of(user)
        userMapper.toDto(_) >> userDto

        when:
        def actual = userService.getUserByUsername("johndoe")

        then:
        actual.id == "1"
        actual.username == "johndoe"
        actual.email == "johndoe@gmail.com"
        1 * userRepository.findByUsernameAndIsDeletedFalse(_) >> Optional.of(user)
        1 * userMapper.toDto(_) >> userDto
    }

    def "get user by username should throw exception if user not found"() {
        given:
        userRepository.findByUsernameAndIsDeletedFalse(_) >> Optional.empty()

        when:
        userService.getUserByUsername("johndoe")

        then:
        thrown(RuntimeException)
        1 * userRepository.findByUsernameAndIsDeletedFalse(_) >> Optional.empty()
    }

    def "get all users"() {
        given:
        def user = ModelGeneratorUtil.generateUser()
        def userDto = DtoGeneratorUtil.generateUserDto()

        userRepository.findAllByIsDeletedFalse() >> [user]
        userMapper.toDto(user) >> userDto

        when:
        def actual = userService.getAllUsers()

        then:
        actual.size() == 1
        actual[0].id == "1"
        actual[0].username == "johndoe"
        actual[0].email == "johndoe@gmail.com"
        1 * userRepository.findAllByIsDeletedFalse() >> [user]
        1 * userMapper.toDto(user) >> userDto
    }

    def "update user"() {
        given:
        def userUpdateRequestDto = DtoGeneratorUtil.generateUserUpdateRequestDto()
        def user = new User(
                id: "1",
                firstName: "David",
                lastName: "Queen",
                username: "davidqueen",
                password: "encodedPassword",
                email: "davidqueen@gmail.com",
                age: 22
        )
        def updatedUser = new User(
                firstName: "Max",
                lastName: "Doe",
                username: "johndoe",
                password: "encodedPassword",
                email: "johndoe@gmail.com",
                age: 25
        )
        def userDto = DtoGeneratorUtil.generateUserDto()

        userRepository.findByIdAndIsDeletedFalse(_) >> Optional.of(user)
        userMapper.toModel(_) >> updatedUser
        userRepository.save(_) >> updatedUser
        userMapper.toDto(_) >> userDto

        when:
        def actual = userService.updateUser("1", userUpdateRequestDto)

        then:
        actual.id == "1"
        actual.username == "johndoe"
        actual.email == "johndoe@gmail.com"
        1 * userRepository.findByIdAndIsDeletedFalse(_) >> Optional.of(user)
        1 * userMapper.toModel(_) >> updatedUser
        1 * userRepository.save(_) >> updatedUser
        1 * userMapper.toDto(_) >> userDto
    }

    def "update user should throw exception if user not found"() {
        given:
        userRepository.findByIdAndIsDeletedFalse(_) >> Optional.empty()

        when:
        userService.updateUser("1", new UserUpdateRequestDto())

        then:
        thrown(RuntimeException)
        1 * userRepository.findByIdAndIsDeletedFalse(_) >> Optional.empty()
    }

    def "delete user by id"() {
        given:
        def user = ModelGeneratorUtil.generateUser()

        userRepository.findByIdAndIsDeletedFalse(_) >> Optional.of(user)

        when:
        userService.deleteUserById("1")

        then:
        1 * userRepository.findByIdAndIsDeletedFalse(_) >> Optional.of(user)
        1 * userRepository.save(_) >> _
    }

    def "delete user by id should throw exception if user not found"() {
        given:
        userRepository.findByIdAndIsDeletedFalse(_) >> Optional.empty()

        when:
        userService.deleteUserById("1")

        then:
        thrown(RuntimeException)
        1 * userRepository.findByIdAndIsDeletedFalse(_) >> Optional.empty()
    }

    def "get followers"() {
        given:
        GroovySpy(SecurityUtil, global: true)
        def user = ModelGeneratorUtil.generateUser()
        def userDto2 = new UserDto(
                id: "2",
                username: "johndoe",
                email: "johndoe2@gmail.com"
        )
        def userDto3 = new UserDto(
                id: "3",
                username: "janedoe",
                email: "johndoe3@gmail.com"
        )

        SecurityUtil.getCurrentUserId() >> "1"
        userRepository.findByIdAndIsDeletedFalse(_) >> Optional.of(user)
        userRepository.findAllByIdInAndIsDeletedFalse(_) >> [new User(id: "2"), new User(id: "3")]
        userMapper.toDto(_) >> userDto2
        userMapper.toDto(_) >> userDto3

        when:
        def actual = userService.getFollowers()

        then:
        actual.size() == 2
        actual[0].id == "2"
        actual[1].id == "3"
        1 * userRepository.findByIdAndIsDeletedFalse(_) >> Optional.of(user)
        1 * userRepository.findAllByIdInAndIsDeletedFalse(_) >> [new User(id: "2"), new User(id: "3")]
        1 * userMapper.toDto(_) >> userDto2
        1 * userMapper.toDto(_) >> userDto3
    }

    def "get followers should throw exception if user not found"() {
        given:
        GroovySpy(SecurityUtil, global: true)
        SecurityUtil.getCurrentUserId() >> "1"
        userRepository.findByIdAndIsDeletedFalse(_) >> Optional.empty()

        when:
        userService.getFollowers()

        then:
        thrown(RuntimeException)
        1 * userRepository.findByIdAndIsDeletedFalse(_) >> Optional.empty()
    }

    def "get following"() {
        given:
        GroovySpy(SecurityUtil, global: true)
        def user = ModelGeneratorUtil.generateUser()
        def userDto4 = new UserDto(
                id: "4",
                username: "johndoe",
                email: "johndoe4@gmail.com"
        )
        def userDto5 = new UserDto(
                id: "5",
                username: "janedoe",
                email: "johndoe5@gmail.com"
        )

        SecurityUtil.getCurrentUserId() >> "1"
        userRepository.findByIdAndIsDeletedFalse(_) >> Optional.of(user)

        userRepository.findAllByIdInAndIsDeletedFalse(_) >> [new User(id: "4"), new User(id: "5")]
        userMapper.toDto(_) >> userDto4
        userMapper.toDto(_) >> userDto5

        when:
        def actual = userService.getFollowing()

        then:
        actual.size() == 2
        actual[0].id == "4"
        actual[1].id == "5"
        1 * userRepository.findByIdAndIsDeletedFalse(_) >> Optional.of(user)
        1 * userRepository.findAllByIdInAndIsDeletedFalse(_) >> [new User(id: "4"), new User(id: "5")]
        1 * userMapper.toDto(_) >> userDto4
        1 * userMapper.toDto(_) >> userDto5
    }

    def "get following should throw exception if user not found"() {
        given:
        GroovySpy(SecurityUtil, global: true)
        SecurityUtil.getCurrentUserId() >> "1"
        userRepository.findByIdAndIsDeletedFalse(_) >> Optional.empty()

        when:
        userService.getFollowing()

        then:
        thrown(RuntimeException)
        1 * userRepository.findByIdAndIsDeletedFalse(_) >> Optional.empty()
    }

    def "follow user"() {
        given:
        GroovySpy(SecurityUtil, global: true)
        SecurityUtil.getCurrentUserId() >> "1"
        def currentUser = new User(
                id: "1",
                firstName: "John",
                lastName: "Doe",
                username: "johndoe",
                password: "encodedPassword",
                email: "johndoe@gmail.com",
                age: 25,
                followers: [],
                following: []
        )
        def followingUser = new User(
                id: "2",
                firstName: "Oliver",
                lastName: "Queen",
                username: "oliverqueen",
                password: "encodedPassword",
                email: "oliverqueen@gmail.com",
                age: 30,
                followers: [],
                following: []
        )

        userRepository.findByIdAndIsDeletedFalse("1") >> Optional.of(currentUser)
        userRepository.findByIdAndIsDeletedFalse("2") >> Optional.of(followingUser)
        userRepository.findByIdAndIsDeletedFalse("2") >> Optional.of(followingUser)

        when:
        userService.followUser("2")

        then:
        currentUser.getFollowing().size() == 1
        followingUser.getFollowers().size() == 1
        1 * userRepository.findByIdAndIsDeletedFalse("1") >> Optional.of(currentUser)
        1 * userRepository.findByIdAndIsDeletedFalse("2") >> Optional.of(followingUser)
    }

    def "follow user should throw exception if current user not found"() {
        given:
        GroovySpy(SecurityUtil, global: true)
        SecurityUtil.getCurrentUserId() >> "1"

        userRepository.findByIdAndIsDeletedFalse("1") >> Optional.empty()

        when:
        userService.followUser("2")

        then:
        thrown(EntityNotFoundException)
    }

    def "follow user should throw exception if following user not found"() {
        given:
        GroovySpy(SecurityUtil, global: true)
        SecurityUtil.getCurrentUserId() >> "1"

        def currentUser = ModelGeneratorUtil.generateUser()

        userRepository.findByIdAndIsDeletedFalse("1") >> Optional.of(currentUser)
        userRepository.findByIdAndIsDeletedFalse("2") >> Optional.empty()

        when:
        userService.followUser("2")

        then:
        thrown(EntityNotFoundException)
    }

    def "follow user should throw exception if user tries to follow himself"() {
        given:
        GroovySpy(SecurityUtil, global: true)
        SecurityUtil.getCurrentUserId() >> "1"

        def currentUser = ModelGeneratorUtil.generateUser()

        userRepository.findByIdAndIsDeletedFalse("1") >> Optional.of(currentUser)

        when:
        userService.followUser("1")

        then:
        thrown(IllegalArgumentException)
    }

    def "unfollow user"() {
        given:
        GroovySpy(SecurityUtil, global: true)
        SecurityUtil.getCurrentUserId() >> "1"

        def currentUser = new User(
                id: "1",
                firstName: "John",
                lastName: "Doe",
                username: "johndoe",
                password: "encodedPassword",
                email: "johndoe@gmail.com",
                age: 25,
                followers: ["0"],
                following: ["2"]
        )
        def followingUser = new User(
                id: "2",
                firstName: "Oliver",
                lastName: "Queen",
                username: "oliverqueen",
                password: "encodedPassword",
                email: "oliverquuen@gmail.com",
                age: 30,
                followers: ["1"],
                following: ["0"]
        )

        userRepository.findByIdAndIsDeletedFalse("1") >> Optional.of(currentUser)
        userRepository.findByIdAndIsDeletedFalse("2") >> Optional.of(followingUser)

        when:
        userService.unfollowUser("2")

        then:
        currentUser.getFollowing().size() == 0
        followingUser.getFollowers().size() == 0
        1 * userRepository.findByIdAndIsDeletedFalse("1") >> Optional.of(currentUser)
        1 * userRepository.findByIdAndIsDeletedFalse("2") >> Optional.of(followingUser)
    }

    def "unfollow user should throw exception if current user not found"() {
        given:
        GroovySpy(SecurityUtil, global: true)
        SecurityUtil.getCurrentUserId() >> "1"

        userRepository.findByIdAndIsDeletedFalse("1") >> Optional.empty()

        when:
        userService.unfollowUser("2")

        then:
        thrown(EntityNotFoundException)
    }

    def "unfollow user should throw exception if following user not found"() {
        given:
        GroovySpy(SecurityUtil, global: true)
        SecurityUtil.getCurrentUserId() >> "1"

        def currentUser = ModelGeneratorUtil.generateUser()

        userRepository.findByIdAndIsDeletedFalse("1") >> Optional.of(currentUser)
        userRepository.findByIdAndIsDeletedFalse("2") >> Optional.empty()

        when:
        userService.unfollowUser("2")

        then:
        thrown(EntityNotFoundException)
    }

    def "unfollow user should throw exception if user tries to unfollow himself"() {
        given:
        GroovySpy(SecurityUtil, global: true)
        SecurityUtil.getCurrentUserId() >> "1"

        def currentUser = ModelGeneratorUtil.generateUser()

        userRepository.findByIdAndIsDeletedFalse("1") >> Optional.of(currentUser)

        when:
        userService.unfollowUser("1")

        then:
        thrown(IllegalArgumentException)
    }
}
