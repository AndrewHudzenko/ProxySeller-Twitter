package com.proxyseller.twitter.mapper.comment

import com.proxyseller.twitter.dto.comment.CommentCreateRequestDto
import com.proxyseller.twitter.dto.comment.CommentDto
import com.proxyseller.twitter.model.comment.Comment
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface CommentMapper {
    Comment toModel(CommentDto commentDto);

    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "userId", expression = "java(com.proxyseller.twitter.security.SecurityUtil.getCurrentUserId())")
    Comment toModel(CommentCreateRequestDto commentCreateRequestDto);

    CommentDto toDto(Comment comment);

}