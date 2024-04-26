package com.proxyseller.twitter.controller.like

import com.proxyseller.twitter.service.like.LikeService
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/likes")
class LikeController {

    private final LikeService likeService

    @PostMapping
    void likePost(@RequestParam UUID postId) {
        likeService.likePost(postId)
    }

}
