package com.proxyseller.twitter.mapper.like

import com.proxyseller.twitter.dto.like.LikePostRequestDto
import com.proxyseller.twitter.dto.like.LikeDto
import com.proxyseller.twitter.model.like.Like
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface LikeMapper {

    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "userId", expression = "java(com.proxyseller.twitter.security.SecurityUtil.getCurrentUserId())")
    Like toModel(LikePostRequestDto likeCreateRequestDto);

    Like toModel(LikeDto likeDto);

    LikeDto toDto(Like like);
}