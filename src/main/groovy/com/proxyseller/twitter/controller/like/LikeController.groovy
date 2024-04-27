package com.proxyseller.twitter.controller.like

import com.proxyseller.twitter.dto.like.LikeDto
import com.proxyseller.twitter.dto.like.LikePostRequestDto
import com.proxyseller.twitter.dto.like.UnlikePostRequestDto
import com.proxyseller.twitter.service.like.LikeService
import groovy.util.logging.Slf4j
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
@RequestMapping("/api/v1/likes")
class LikeController {

    private final LikeService likeService

    LikeController(LikeService likeService) {
        this.likeService = likeService
    }

    @PostMapping("/like")
    LikeDto likePost(@RequestBody LikePostRequestDto likeCreateRequestDto) {
        log.info("Like controller: like post with id: ${likeCreateRequestDto.getPostId()}")
        return likeService.likePost(likeCreateRequestDto)
    }

    @PostMapping("/unlike")
    void unlikePost(@RequestBody UnlikePostRequestDto unlikePostRequestDto) {
        log.info("Like controller: unlike post with id: ${unlikePostRequestDto.getPostId()}")
        likeService.unlikePost(unlikePostRequestDto)
    }

    @GetMapping("/post/count/{id}")
    int getLikesCountByPostId(@PathVariable(name = "id") String id) {
        log.info("Like controller: get likes count by post id: $id")
        return likeService.getLikesCount(id)
    }

    @GetMapping("/post/{id}")
    List<LikeDto> getLikesByPostId(@PathVariable(name = "id") String id) {
        log.info("Like controller: get likes by post id: $id")
        return likeService.getLikesByPostId(id)
    }
}
