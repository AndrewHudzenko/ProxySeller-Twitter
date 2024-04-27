package com.proxyseller.twitter.controller.comment

import com.proxyseller.twitter.dto.comment.CommentCreateRequestDto
import com.proxyseller.twitter.dto.comment.CommentDto
import com.proxyseller.twitter.dto.comment.CommentUpdateRequestDto
import com.proxyseller.twitter.service.comment.CommentService
import groovy.util.logging.Slf4j
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
@RequestMapping("/api/v1/comments")
class CommentController {

    private final CommentService commentService

    CommentController(CommentService commentService) {
        this.commentService = commentService
    }

    @PostMapping
    CommentDto createComment(@RequestBody CommentCreateRequestDto commentCreateRequestDto) {
        log.info("Comment controller: creating comment, commentCreateRequestDto: $commentCreateRequestDto")
        return commentService.createComment(commentCreateRequestDto)
    }

    @GetMapping("/post/{id}")
    List<CommentDto> getCommentsByPostId(@PathVariable(name = "id") String id) {
        log.info("Comment controller: getting comments by post id: $id")
        return commentService.getCommentsByPostId(id)
    }

    @GetMapping("/user/{id}")
    List<CommentDto> getCommentsByUserId(@PathVariable(name = "id") String id) {
        log.info("Comment controller: getting comments by user id: $id")
        return commentService.getCommentsByUserId(id)
    }

    @GetMapping("/{id}")
    CommentDto getCommentById(@PathVariable(name = "id") String id) {
        log.info("Comment controller: getting comment by id: $id")
        return commentService.getCommentById(id)
    }

    @PatchMapping("/{id}")
    CommentDto updateComment(@PathVariable(name = "id") String id, @RequestBody CommentUpdateRequestDto commentUpdateRequestDto) {
        log.info("Comment controller: updating comment by id: $id")
        return commentService.updateComment(id, commentUpdateRequestDto)
    }

    @DeleteMapping("/{id}")
    void deleteComment(@PathVariable(name = "id") String id) {
        log.info("Comment controller: deleting comment by id: $id")
        commentService.deleteComment(id)
    }
}
