package com.proxyseller.twitter.dto.like

class UnlikePostRequestDto {
    private String postId;

    public UnlikePostRequestDto() {
    }

    public UnlikePostRequestDto(String postId) {
        this.postId = postId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
