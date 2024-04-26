package com.proxyseller.twitter.model.like


import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import java.time.Instant

@Document(collection = "likes")
class Like {
    @Id
    private String id;
    private String userId;
    private String postId
    private Instant createdAt;
    private boolean isDeleted = false;

    Like() {
    }

    Like(String id, String userId, String postId, Instant createdAt, boolean isDeleted) {
        this.id = id
        this.userId = userId
        this.postId = postId
        this.createdAt = createdAt
        this.isDeleted = isDeleted
    }

    String getId() {
        return id
    }

    void setId(String id) {
        this.id = id
    }

    String getUserId() {
        return userId
    }

    void setUserId(String userId) {
        this.userId = userId
    }

    String getPostId() {
        return postId
    }

    void setPostId(String postId) {
        this.postId = postId
    }

    Instant getCreatedAt() {
        return createdAt
    }

    void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt
    }

    boolean getIsDeleted() {
        return isDeleted
    }

    void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted
    }
}
