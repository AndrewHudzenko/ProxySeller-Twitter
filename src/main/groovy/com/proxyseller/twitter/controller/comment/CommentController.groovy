package com.proxyseller.twitter.controller.comment

import com.proxyseller.twitter.dto.comment.CommentCreateRequestDto
import com.proxyseller.twitter.dto.comment.CommentDto
import com.proxyseller.twitter.service.comment.CommentService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/comments")
class CommentController {

    private final CommentService commentService

    CommentController(CommentService commentService) {
        this.commentService = commentService
    }

    @PostMapping
    CommentDto createComment(@RequestBody CommentCreateRequestDto commentCreateRequestDto) {
        return commentService.createComment(commentCreateRequestDto)
    }

    @GetMapping("/post/{postId}")
    List<CommentDto> getCommentsByPostId(@PathVariable String postId) {
        return commentService.getCommentsByPostId(postId)
    }

    @GetMapping("/user/{userId}")
    List<CommentDto> getCommentsByUserId(@PathVariable String userId) {
        return commentService.getCommentsByUserId(userId)
    }

    @GetMapping("/{id}")
    CommentDto getCommentById(@PathVariable String id) {
        return commentService.getCommentById(id)
    }
}
