package com.proxyseller.twitter.service.post.impl

import com.proxyseller.twitter.dto.post.PostCreateRequestDto
import com.proxyseller.twitter.dto.post.PostDto
import com.proxyseller.twitter.dto.post.PostUpdateRequestDto
import com.proxyseller.twitter.mapper.post.PostMapper
import com.proxyseller.twitter.model.post.Post
import com.proxyseller.twitter.model.user.User
import com.proxyseller.twitter.repository.post.PostRepository
import com.proxyseller.twitter.repository.user.UserRepository
import com.proxyseller.twitter.security.SecurityUtil
import com.proxyseller.twitter.service.post.PostService
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service

import java.util.stream.Collectors

@Slf4j
@Service
class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;

    PostServiceImpl(PostRepository postRepository, PostMapper postMapper, UserRepository userRepository) {
        this.postRepository = postRepository
        this.postMapper = postMapper

        this.userRepository = userRepository
    }

    @Override
    PostDto createPost(PostCreateRequestDto createPostRequestDto) {
        log.info("PostServiceImpl: Creating post: ${createPostRequestDto}")

        Post post = postMapper.toModel(createPostRequestDto);
//
//        String currentUserId = SecurityUtil.getCurrentUserId()
//        User currentUser = userRepository.findByIdAndIsDeletedFalse(currentUserId)
//                .orElseThrow(() -> new RuntimeException("User not found!"))

        // TODO: userId and postedAd fields to mapper
//        post.setUserId(currentUser.getId())
//        post.setPostedAt(Instant.now())

        return postMapper.toDto(postRepository.insert(post))
    }

    @Override
    PostDto getPostById(String id) {
        log.info("PostServiceImpl: Getting post by id: ${id}")
        return postMapper.toDto(postRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Post not found!")))
    }

    @Override
    List<PostDto> getAllPosts() {
        log.info("PostServiceImpl: Getting all posts")
        return postRepository.findAllByIsDeletedFalse().stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList())
    }

    @Override
    List<PostDto> getAllPostsByCurrentUser() {
        String userId = SecurityUtil.getCurrentUserId()
        log.info("PostServiceImpl: Getting all posts by current user: ${userId}")
        return postRepository.findAllByUserIdAndIsDeletedFalse(userId).stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList())
    }

    @Override
    PostDto updatePost(PostDto postDto) {
        log.info("PostServiceImpl: Updating post: ${postDto}")
        return postMapper.toDto(postRepository.save(postMapper.toModel(postDto)))
    }

    @Override
    PostDto updatePost(String id, PostUpdateRequestDto updatePostRequestDto) {
        Post post = postRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Post not found!"))
        post.setLocation(updatePostRequestDto.getLocation())
        post.setImageUrl(updatePostRequestDto.getImageUrl())
        post.setContent(updatePostRequestDto.getContent())

        log.info("PostServiceImpl: Updating post by id: ${id}, ${updatePostRequestDto}")
        return postMapper.toDto(postRepository.save(post))
    }

    @Override
    void deletePostById(String id) {
        Post post = postRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Post not found!"))
        post.setIsDeleted(true)

        log.info("PostServiceImpl: Deleting post by id: ${id}")
        postRepository.save(post)
    }

    @Override
    List<PostDto> getAllPostsByUserId(String id) {
        log.info("PostServiceImpl: Getting all posts by user id: ${id}")

        return postRepository.findAllByUserIdAndIsDeletedFalse(id).stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList())
    }

    @Override
    List<PostDto> getAllPostsByFollowingUsers() {
        String currentUserId = securityUtil.getCurrentUserId()
        User currentUser = userRepository.findByIdAndIsDeletedFalse(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found!"))

        log.info("PostServiceImpl: Getting all posts by following users")
        return postRepository.findAllByUserIdInAndIsDeletedFalse(currentUser.getFollowing()).stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList())
    }

    @Override
    Post getPostModelById(String id) {
        log.info("PostServiceImpl: Getting post model by id: ${id}")
        return postRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Post not found!"))
    }
}
