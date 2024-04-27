package com.proxyseller.twitter.dto.like

class LikePostRequestDto {
    private String postId

    LikePostRequestDto() {
    }

    LikePostRequestDto(String postId) {
        this.postId = postId
    }

    String getPostId() {
        return postId
    }

    void setPostId(String postId) {
        this.postId = postId
    }

}