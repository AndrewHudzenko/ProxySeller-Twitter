package com.proxyseller.twitter.mapper.post

import com.proxyseller.twitter.dto.post.PostCreateRequestDto
import com.proxyseller.twitter.dto.post.PostDto
import com.proxyseller.twitter.dto.post.PostUpdateRequestDto
import com.proxyseller.twitter.model.post.Post
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface PostMapper {
    PostDto toDto(Post post);

    Post toModel(PostDto postDto);

    @Mapping(target = "userId", expression = "java(com.proxyseller.twitter.security.SecurityUtil.getCurrentUserId())")
    @Mapping(target = "postedAt", expression = "java(java.time.Instant.now())")
    Post toModel(PostCreateRequestDto postCreateRequestDto);

    Post toModel(PostUpdateRequestDto postUpdateRequestDto);


}