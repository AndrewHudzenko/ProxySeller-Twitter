package com.proxyseller.twitter.mapper.comment

import com.proxyseller.twitter.dto.comment.CommentCreateRequestDto
import com.proxyseller.twitter.dto.comment.CommentDto
import com.proxyseller.twitter.model.comment.Comment
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface CommentMapper {
    Comment toModel(CommentDto commentDto);

    Comment toModel(CommentCreateRequestDto commentCreateRequestDto);

    CommentDto toDto(Comment comment);

}