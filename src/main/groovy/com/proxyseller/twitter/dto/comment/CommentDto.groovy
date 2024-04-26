package com.proxyseller.twitter.dto.comment

class CommentDto {
    private String id;
    private String content;
    private String userId;
    private String postId;

    CommentDto() {
    }

    CommentDto(String id, String content, String userId, String postId) {
        this.id = id
        this.content = content
        this.userId = userId
        this.postId = postId
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
}
