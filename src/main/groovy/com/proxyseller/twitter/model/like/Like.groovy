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

    boolean equals(o) {
        if (this.is(o)) return true
        if (o == null || getClass() != o.class) return false

        Like like = (Like) o

        if (isDeleted != like.isDeleted) return false
        if (createdAt != like.createdAt) return false
        if (id != like.id) return false
        if (postId != like.postId) return false
        if (userId != like.userId) return false

        return true
    }

    int hashCode() {
        int result
        result = (id != null ? id.hashCode() : 0)
        result = 31 * result + (userId != null ? userId.hashCode() : 0)
        result = 31 * result + (postId != null ? postId.hashCode() : 0)
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0)
        result = 31 * result + (isDeleted ? 1 : 0)
        return result
    }
}
