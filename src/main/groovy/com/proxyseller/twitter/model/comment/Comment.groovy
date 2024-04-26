package com.proxyseller.twitter.model.comment

import org.springframework.data.mongodb.core.mapping.Document

import java.time.Instant

@Document(collection = "comments")
class Comment {
    private String id;
    private String content;
    private Instant createdAt;
    private String userId;
    private String postId;
    private boolean isDeleted = false;

    Comment() {
    }

    Comment(String id, String content, Instant createdAt, String userId, String postId, boolean isDeleted) {
        this.id = id
        this.content = content
        this.createdAt = createdAt
        this.userId = userId
        this.postId = postId
        this.isDeleted = isDeleted
    }

    String getId() {
        return id
    }

    void setId(String id) {
        this.id = id
    }

    String getContent() {
        return content
    }

    void setContent(String content) {
        this.content = content
    }

    Instant getCreatedAt() {
        return createdAt
    }

    void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt
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

    boolean getIsDeleted() {
        return isDeleted
    }

    void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted
    }
}
