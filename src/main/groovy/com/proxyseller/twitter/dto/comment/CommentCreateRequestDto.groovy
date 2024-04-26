package com.proxyseller.twitter.dto.comment

class CommentCreateRequestDto {
    private String content;
    private String postId;

    CommentCreateRequestDto() {
    }

    CommentCreateRequestDto(String content, String postId) {
        this.content = content
        this.postId = postId
    }

    String getContent() {
        return content
    }

    void setContent(String content) {
        this.content = content
    }

    String getPostId() {
        return postId
    }

    void setPostId(String postId) {
        this.postId = postId
    }
}
