package com.proxyseller.twitter.service.like

/**
 * Interface to define the methods for like service
 */
interface LikeService {
    /**
     * Method to like a post by post id
     * @param postId
     */
    void likePost(UUID postId);

    /**
     * Method to unlike a post by post id
     * @param postId
     */
    void unlikePost(UUID postId);
}
