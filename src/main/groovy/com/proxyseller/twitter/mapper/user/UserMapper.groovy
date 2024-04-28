package com.proxyseller.twitter.mapper.user

import com.proxyseller.twitter.dto.user.UserDto
import com.proxyseller.twitter.dto.user.UserRegistrationRequestDto
import com.proxyseller.twitter.dto.user.UserUpdateRequestDto
import com.proxyseller.twitter.model.user.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface UserMapper {

    UserDto toDto(User user);

    @Mapping(target = "password", expression = "java(com.proxyseller.twitter.security.SecurityUtil.encodePassword(userUpdateRequestDto.getPassword()))")
    User toModel(UserUpdateRequestDto userUpdateRequestDto);

}