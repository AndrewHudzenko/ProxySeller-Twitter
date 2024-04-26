package com.proxyseller.twitter.mapper.like

import com.proxyseller.twitter.dto.like.LikeCreateRequestDto
import com.proxyseller.twitter.dto.like.LikeDto
import com.proxyseller.twitter.model.like.Like
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface LikeMapper {
    Like toModel(LikeCreateRequestDto likeCreateRequestDto);

    LikeDto toDto(Like like);

}