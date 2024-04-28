package com.proxyseller.twitter.service.like.impl

import com.proxyseller.twitter.dto.like.LikeDto
import com.proxyseller.twitter.dto.like.LikePostRequestDto
import com.proxyseller.twitter.dto.like.UnlikePostRequestDto
import com.proxyseller.twitter.mapper.like.LikeMapper
import com.proxyseller.twitter.mapper.post.PostMapper
import com.proxyseller.twitter.model.like.Like
import com.proxyseller.twitter.model.post.Post
import com.proxyseller.twitter.repository.like.LikeRepository
import com.proxyseller.twitter.security.SecurityUtil
import com.proxyseller.twitter.service.like.LikeService
import com.proxyseller.twitter.service.post.PostService
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service

import java.util.stream.Collectors

@Slf4j
@Service
class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final PostService postService;
    private final LikeMapper likeMapper;
    private final PostMapper postMapper;

    LikeServiceImpl(LikeRepository likeRepository, PostService postService, LikeMapper likeMapper, PostMapper postMapper) {
        this.likeRepository = likeRepository
        this.postService = postService
        this.likeMapper = likeMapper
        this.postMapper = postMapper
    }

    @Override
    // TODO: Need to set Mongo replica set to enable transactions
//    @Transactional
    LikeDto likePost(LikePostRequestDto likeCreateRequestDto) {
        log.info("Like service: like post with id: ${likeCreateRequestDto.getPostId()}")
        Like like = likeMapper.toModel(likeCreateRequestDto)
        LikeDto likeDto = likeMapper.toDto(likeRepository.save(like))

        log.info("Like service: update post with id: ${likeCreateRequestDto.getPostId()}")
        Post post = postService.getPostModelById(likeCreateRequestDto.getPostId())

        post.addLike(like)
        postService.updatePost(postMapper.toDto(post))
        return likeDto;
    }

    @Override
    // TODO: Need to set Mongo replica set to enable transactions
//    @Transactional
    void unlikePost(UnlikePostRequestDto unlikePostRequestDto) {
        log.info("Like service: unlike post with id: ${unlikePostRequestDto.getPostId()}")
        Like like = likeRepository.findAllByPostIdAndUserId(unlikePostRequestDto.getPostId(), SecurityUtil.getCurrentUserId()).stream()
                .findFirst()
                .orElseThrow { new RuntimeException("Like not found") }
        likeRepository.delete(like)

        log.info("Like service: update post with id: ${unlikePostRequestDto.getPostId()}")
        Post post = postService.getPostModelById(unlikePostRequestDto.getPostId())

        post.removeLike(like)
        postService.updatePost(postMapper.toDto(post))
    }

    @Override
    int getLikesCount(String id) {
        log.info("Like service: get likes count by post id: $id")
        return likeRepository.findByPostId(id).size()
    }

    @Override
    List<LikeDto> getLikesByPostId(String id) {
        log.info("Like service: get likes by post id: $id")
        return postService.getPostModelById(id).getLikes().stream()
                .map { likeMapper.toDto(it) }
                .collect(Collectors.toList())
    }
}
