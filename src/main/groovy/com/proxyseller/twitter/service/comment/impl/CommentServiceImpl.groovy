package com.proxyseller.twitter.service.comment.impl

import com.proxyseller.twitter.dto.comment.CommentCreateRequestDto
import com.proxyseller.twitter.dto.comment.CommentDto
import com.proxyseller.twitter.mapper.comment.CommentMapper
import com.proxyseller.twitter.mapper.post.PostMapper
import com.proxyseller.twitter.model.comment.Comment
import com.proxyseller.twitter.model.post.Post
import com.proxyseller.twitter.repository.comment.CommentRepository
import com.proxyseller.twitter.repository.post.PostRepository
import com.proxyseller.twitter.security.SecurityUtil
import com.proxyseller.twitter.service.comment.CommentService
import com.proxyseller.twitter.service.post.PostService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.time.Instant
import java.util.stream.Collectors

@Service
class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository
    private final CommentMapper commentMapper
    private final SecurityUtil securityUtil
    private final PostService postService
    private final PostRepository postRepository
    private final PostMapper postMapper

    CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper, SecurityUtil securityUtil, PostService postService, PostRepository postRepository, PostMapper postMapper) {
        this.commentRepository = commentRepository
        this.commentMapper = commentMapper
        this.securityUtil = securityUtil
        this.postService = postService
        this.postRepository = postRepository
        this.postMapper = postMapper
    }

    @Override
    @Transactional
    // TODO: FIX -> Transactional doesn't work here
    // TODO: clean up code (mapper and repository)
    CommentDto createComment(CommentCreateRequestDto commentCreateRequestDto) {
        Post post = postRepository.findById(commentCreateRequestDto.getPostId()).orElse(null)

//        PostDto post = postService.getPostById(commentCreateRequestDto.getPostId())
        if (post == null) {
            throw new IllegalArgumentException("Post not found")
        }

        Comment comment = commentMapper.toModel(commentCreateRequestDto)
        comment.setCreatedAt(Instant.now())
        comment.setUserId(securityUtil.getCurrentUserId())
        commentRepository.insert(comment)

        post.addComment(comment)
        postService.updatePost(postMapper.toDto(post))

        return commentMapper.toDto(comment)
    }

    @Override
    List<CommentDto> getCommentsByPostId(String postId) {
        return commentRepository.findAllByPostId(postId).stream()
                .map {commentMapper.toDto(it)}
                .collect(Collectors.toList())
    }

    @Override
    List<CommentDto> getCommentsByUserId(String userId) {
        return commentRepository.findAllByUserId(userId).stream()
                .map {commentMapper.toDto(it)}
                .collect(Collectors.toList())
    }

    @Override
    CommentDto getCommentById(String id) {
        return commentMapper.toDto(commentRepository.findById(id).orElse(null))
    }
}
