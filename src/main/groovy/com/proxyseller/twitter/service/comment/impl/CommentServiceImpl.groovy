package com.proxyseller.twitter.service.comment.impl


import com.proxyseller.twitter.dto.comment.CommentCreateRequestDto
import com.proxyseller.twitter.dto.comment.CommentDto
import com.proxyseller.twitter.dto.comment.CommentUpdateRequestDto
import com.proxyseller.twitter.exception.EntityNotFoundException
import com.proxyseller.twitter.mapper.comment.CommentMapper
import com.proxyseller.twitter.mapper.post.PostMapper
import com.proxyseller.twitter.model.comment.Comment
import com.proxyseller.twitter.model.post.Post
import com.proxyseller.twitter.repository.comment.CommentRepository
import com.proxyseller.twitter.repository.post.PostRepository
import com.proxyseller.twitter.service.comment.CommentService
import com.proxyseller.twitter.service.post.PostService
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service

import java.util.stream.Collectors

@Slf4j
@Service
class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository
    private final CommentMapper commentMapper
    private final PostService postService
    private final PostRepository postRepository
    private final PostMapper postMapper

    CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper, PostService postService, PostRepository postRepository, PostMapper postMapper) {
        this.commentRepository = commentRepository
        this.commentMapper = commentMapper
        this.postService = postService
        this.postRepository = postRepository
        this.postMapper = postMapper
    }

    @Override
//    @Transactional
    // TODO: FIX -> Transactional doesn't work here
    CommentDto createComment(CommentCreateRequestDto commentCreateRequestDto) {
        Post post = postRepository.findById(commentCreateRequestDto.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("Post not found"))

        Comment comment = commentMapper.toModel(commentCreateRequestDto)

        log.info("Comment service: creating comment, request: $commentCreateRequestDto")
        commentRepository.insert(comment)
        post.addComment(comment)
        postService.updatePost(postMapper.toDto(post))

        return commentMapper.toDto(comment)
    }

    @Override
    List<CommentDto> getCommentsByPostId(String postId) {
        return commentRepository.findAllByPostIdAndIsDeletedFalse(postId).stream()
                .map {commentMapper.toDto(it)}
                .collect(Collectors.toList())
    }

    @Override
    List<CommentDto> getCommentsByUserId(String userId) {
        return commentRepository.findAllByUserIdAndIsDeletedFalse(userId).stream()
                .map {commentMapper.toDto(it)}
                .collect(Collectors.toList())
    }

    @Override
    CommentDto getCommentById(String id) {
        return commentMapper.toDto(commentRepository.findByIdAndIsDeletedFalse(id)
        .orElseThrow(() -> new EntityNotFoundException("Comment not found")))
    }


    @Override
    void deleteComment(String id) {
        Comment comment = commentRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"))
        log.info("Comment service: deleting comment, by id: $id")
        comment.setIsDeleted(true)
        commentRepository.save(comment)
    }

    @Override
//    @Transactional
    CommentDto updateComment(String id, CommentUpdateRequestDto commentUpdateRequestDto) {
        Comment comment = commentRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"))

        log.info("Comment service: updating comment, by id: $id, request: $commentUpdateRequestDto")
        comment.setContent(commentUpdateRequestDto.getContent())
        commentMapper.toDto(commentRepository.save(comment))

        Post post = postRepository.findById(comment.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("Post not found"))

        log.info("Comment service: updating post, by id: ${post.getId()}, request: $commentUpdateRequestDto")
        post.getComments().stream()
                .filter {it.getId().equals(id)}
                .findFirst()
                .ifPresent {it.setContent(commentUpdateRequestDto.getContent())}
        postService.updatePost(postMapper.toDto(post))
        return commentMapper.toDto(comment)
    }
}
