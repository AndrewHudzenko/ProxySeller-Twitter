package com.proxyseller.twitter.dto.like


import java.time.Instant

class LikeDto {
    private String id
    private String postId
    private String userId
    private Instant createdAt

    LikeDto() {
    }

    LikeDto(String id, String postId, String userId, Instant createdAt) {
        this.id = id
        this.postId = postId
        this.userId = userId
        this.createdAt = createdAt
    }

    String getId() {
        return id
    }

    void setId(String id) {
        this.id = id
    }

    String getPostId() {
        return postId
    }

    void setPostId(String postId) {
        this.postId = postId
    }

    String getUserId() {
        return userId
    }

    void setUserId(String userId) {
        this.userId = userId
    }

    Instant getCreatedAt() {
        return createdAt
    }

    void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt
    }
}