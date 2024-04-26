package com.proxyseller.twitter.dto.like

class LikeCreateRequestDto {
    private String postId

    LikeCreateRequestDto() {
    }

    LikeCreateRequestDto(String postId) {
        this.postId = postId
    }

    String getPostId() {
        return postId
    }

    void setPostId(String postId) {
        this.postId = postId
    }

}