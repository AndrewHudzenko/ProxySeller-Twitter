package com.proxyseller.twitter.junit5

import com.proxyseller.twitter.dto.user.UserDto
import com.proxyseller.twitter.dto.user.UserRegistrationRequestDto
import com.proxyseller.twitter.dto.user.UserUpdateRequestDto
import com.proxyseller.twitter.mapper.user.UserMapper
import com.proxyseller.twitter.model.user.User
import com.proxyseller.twitter.repository.user.UserRepository
import com.proxyseller.twitter.security.SecurityUtil
import com.proxyseller.twitter.service.user.impl.UserServiceImpl
import org.instancio.Instancio
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockedStatic
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.crypto.password.PasswordEncoder

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.mockito.ArgumentMatchers.any
import static org.mockito.ArgumentMatchers.anyString
import static org.mockito.Mockito.times
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository
    @Mock
    private UserMapper userMapper
    @Mock
    private PasswordEncoder passwordEncoder
    @InjectMocks
    private UserServiceImpl userServiceImpl

    @Test
    void registerUser() {
        UserRegistrationRequestDto userRegistrationRequestDto = Instancio.create(UserRegistrationRequestDto.class)
        User user = Instancio.create(User.class)
        UserDto userDto = Instancio.create(UserDto.class)

        when(userRepository.findByEmailAndIsDeletedFalse(anyString())).thenReturn(Optional.empty())
        when(userMapper.toDto(any())).thenReturn(userDto)
        when(passwordEncoder.encode(anyString())).thenReturn('password')
        when(userRepository.save(any())).thenReturn(user)

        UserDto actual = userServiceImpl.registerUser(userRegistrationRequestDto)

        assertNotNull(actual)
        assertEquals(userDto, actual)
        verify(userRepository).findByEmailAndIsDeletedFalse(anyString())
        verify(userRepository).save(any())
    }

    @Test
    void getUserById() {
        User user = Instancio.create(User.class)
        UserDto userDto = Instancio.create(UserDto.class)

        when(userRepository.findByIdAndIsDeletedFalse(anyString())).thenReturn(Optional.of(user))
        when(userMapper.toDto(any())).thenReturn(userDto)

        UserDto actual = userServiceImpl.getUserById('id')

        assertNotNull(actual)
        assertEquals(userDto, actual)
        verify(userRepository).findByIdAndIsDeletedFalse(anyString())
    }

    @Test
    void getUserByUsername() {
        User user = Instancio.create(User.class)
        UserDto userDto = Instancio.create(UserDto.class)

        when(userRepository.findByUsernameAndIsDeletedFalse(anyString())).thenReturn(Optional.of(user))
        when(userMapper.toDto(any())).thenReturn(userDto)

        UserDto actual = userServiceImpl.getUserByUsername('username')

        assertNotNull(actual)
        assertEquals(userDto, actual)
        verify(userRepository).findByUsernameAndIsDeletedFalse(anyString())
    }

    @Test
    void getAllUsers() {
        User user = Instancio.create(User.class)
        UserDto userDto = Instancio.create(UserDto.class)

        when(userRepository.findAllByIsDeletedFalse()).thenReturn(List.of(user))
        when(userMapper.toDto(any())).thenReturn(userDto)

        List<UserDto> actual = userServiceImpl.getAllUsers()

        assertNotNull(actual)
        assertEquals(1, actual.size())
        assertEquals(userDto, actual.get(0))
        verify(userRepository).findAllByIsDeletedFalse()
    }

    @Test
    void updateUser() {
        User user = Instancio.create(User.class)
        User updatedUser = Instancio.create(User.class)
        UserDto userDto = Instancio.create(UserDto.class)

        when(userRepository.findByIdAndIsDeletedFalse(anyString())).thenReturn(Optional.of(user))
        when(userMapper.toModel(any())).thenReturn(updatedUser)
        when(userRepository.save(any())).thenReturn(updatedUser)
        when(userMapper.toDto(any())).thenReturn(userDto)

        UserDto actual = userServiceImpl.updateUser('id', Instancio.create(UserUpdateRequestDto.class))

        assertNotNull(actual)
        assertEquals(userDto, actual)
        verify(userRepository).findByIdAndIsDeletedFalse(anyString())
        verify(userRepository).save(any())
    }

    @Test
    void deleteUserById() {
        User user = Instancio.create(User.class)

        when(userRepository.findByIdAndIsDeletedFalse(anyString())).thenReturn(Optional.of(user))

        userServiceImpl.deleteUserById('id')

        verify(userRepository).findByIdAndIsDeletedFalse(anyString())
        verify(userRepository).save(any())
    }

    @Test
    void getFollowers() {
        User user = Instancio.create(User.class)
        UserDto userDto = Instancio.create(UserDto.class)
        MockedStatic mockedStatic = Mockito.mockStatic(SecurityUtil.class)

        mockedStatic.when(SecurityUtil::getCurrentUserId).thenReturn('id')
        when(userRepository.findByIdAndIsDeletedFalse(anyString())).thenReturn(Optional.of(user))
        when(userRepository.findAllByIdInAndIsDeletedFalse(any())).thenReturn(List.of(user))
        when(userMapper.toDto(any())).thenReturn(userDto)

        List<UserDto> actual = userServiceImpl.getFollowers()

        assertNotNull(actual)
        assertEquals(1, actual.size())
        assertEquals(userDto, actual.get(0))
        verify(userRepository).findByIdAndIsDeletedFalse(anyString())
        verify(userRepository).findAllByIdInAndIsDeletedFalse(any())
        mockedStatic.close()
    }

    @Test
    void getFollowing() {
        User user = Instancio.create(User.class)
        UserDto userDto = Instancio.create(UserDto.class)
        MockedStatic mockedStatic = Mockito.mockStatic(SecurityUtil.class)

        mockedStatic.when(SecurityUtil::getCurrentUserId).thenReturn('id')
        when(userRepository.findByIdAndIsDeletedFalse(anyString())).thenReturn(Optional.of(user))
        when(userRepository.findAllByIdInAndIsDeletedFalse(any())).thenReturn(List.of(user))
        when(userMapper.toDto(any())).thenReturn(userDto)

        List<UserDto> actual = userServiceImpl.getFollowing()

        assertNotNull(actual)
        assertEquals(1, actual.size())
        assertEquals(userDto, actual.get(0))
        verify(userRepository).findByIdAndIsDeletedFalse(anyString())
        verify(userRepository).findAllByIdInAndIsDeletedFalse(any())
        mockedStatic.close()
    }

    @Test
    void followUser() {
        MockedStatic mockedStatic = Mockito.mockStatic(SecurityUtil.class)
        User currentUser = Instancio.create(User.class)
        User followingUser = Instancio.create(User.class)

        mockedStatic.when(SecurityUtil::getCurrentUserId).thenReturn('id')
        when(userRepository.findByIdAndIsDeletedFalse(anyString())).thenReturn(Optional.of(currentUser))
        when(userRepository.findByIdAndIsDeletedFalse(anyString())).thenReturn(Optional.of(followingUser))

        userServiceImpl.followUser('id1')

        verify(userRepository, times(3)).findByIdAndIsDeletedFalse(anyString())
        verify(userRepository, times(2)).save(any())
        mockedStatic.close()
    }

    @Test
    void unfollowUser() {
        MockedStatic mockedStatic = Mockito.mockStatic(SecurityUtil.class)
        User currentUser = Instancio.create(User.class)
        User followingUser = Instancio.create(User.class)

        mockedStatic.when(SecurityUtil::getCurrentUserId).thenReturn('id')
        when(userRepository.findByIdAndIsDeletedFalse(anyString())).thenReturn(Optional.of(currentUser))
        when(userRepository.findByIdAndIsDeletedFalse(anyString())).thenReturn(Optional.of(followingUser))

        userServiceImpl.unfollowUser('id1')

        verify(userRepository, times(3)).findByIdAndIsDeletedFalse(anyString())
        verify(userRepository, times(2)).save(any())
        mockedStatic.close()
    }
}
