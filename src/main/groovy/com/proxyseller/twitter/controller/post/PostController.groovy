package com.proxyseller.twitter.controller.post

import com.proxyseller.twitter.dto.post.PostCreateRequestDto
import com.proxyseller.twitter.dto.post.PostDto
import com.proxyseller.twitter.dto.post.PostUpdateRequestDto
import com.proxyseller.twitter.service.post.PostService
import groovy.util.logging.Slf4j
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
@RequestMapping("/api/v1/posts")
class PostController {
    private final PostService postService

    PostController(PostService postService) {
        this.postService = postService
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    PostDto createPost(@RequestBody PostCreateRequestDto createPostRequestDto) {
        log.info("PostController: Creating post: ${createPostRequestDto}")
        return postService.createPost(createPostRequestDto)
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    PostDto getPostById(@PathVariable(name = "id") String id) {
        log.info("PostController: Getting post by id: ${id}")
        return postService.getPostById(id)
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    // TODO: Change to ROLE_ADMIN after migration
    List<PostDto> getAllPosts() {
            log.info("PostController: Getting all posts")
        return postService.getAllPosts()
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    List<PostDto> getAllPostsByCurrentUser() {
        log.info("PostController: Getting all posts by current user")
        return postService.getAllPostsByCurrentUser()
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    List<PostDto> getAllPostsByUserId(@PathVariable(name = "id") String id) {
        log.info("PostController: Getting all posts by user id: ${id}")
        return postService.getAllPostsByUserId(id)
    }

    @GetMapping("/following")
    @PreAuthorize("hasRole('ROLE_USER')")
    List<PostDto> getAllPostsByFollowingUsers() {
        log.info("PostController: Getting all posts by following users")
        return postService.getAllPostsByFollowingUsers()
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    PostDto updatePost(@PathVariable(name = "id") String id, @RequestBody PostUpdateRequestDto postUpdateRequestDto) {
        log.info("PostController: Updating post by id: ${id}, with: ${postUpdateRequestDto}")
        return postService.updatePost(id, postUpdateRequestDto)
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    void deletePostById(@PathVariable(name = "id") String id) {
        log.info("PostController: Deleting post by id: ${id}")
        postService.deletePostById(id)
    }
}
