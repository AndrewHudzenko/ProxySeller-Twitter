package com.proxyseller.twitter.service.post

import com.proxyseller.twitter.dto.post.PostCreateRequestDto
import com.proxyseller.twitter.dto.post.PostDto
import com.proxyseller.twitter.dto.post.PostUpdateRequestDto
import com.proxyseller.twitter.model.post.Post

/**
 * Interface to define the methods for the post service
 */
interface PostService {
    /**
     * Method to create a new post
     * @param createPostRequestDto
     * @return PostDto
     */
    PostDto createPost(PostCreateRequestDto createPostRequestDto);

    /**
     * Method to get a post by id
     * @param id
     * @return PostDto
     */
    PostDto getPostById(String id);

    /**
     * Method to get all posts
     * @return List<PostDto>
     */
    List<PostDto> getAllPosts();

    /**
     * Method to get all posts by current user
     */
    List<PostDto> getAllPostsByCurrentUser();

    /**
     * Method to update a post
     * @param id
     * @param updatePostRequestDto
     * @return PostDto
     */
    PostDto updatePost(String id, PostUpdateRequestDto updatePostRequestDto);

    /**
     * Method to update a post
     * @param postDto
     * @return PostDto
     */
    PostDto updatePost(PostDto postDto);

    /**
     * Method to delete a post by id
     * @param id
     */
    void deletePostById(String id);

    /**
     * Method to get all posts by user id
     * @param id
     * @return List<PostDto>
     */
    List<PostDto> getAllPostsByUserId(String id)

    /**
     * Method to get all posts by following users
     * @return List<PostDto>
     */
    List<PostDto> getAllPostsByFollowingUsers()
}