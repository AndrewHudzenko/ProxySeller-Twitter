package com.proxyseller.twitter.dto.comment

class CommentUpdateRequestDto {
    private String content

    CommentUpdateRequestDto() {
    }

    CommentUpdateRequestDto(String content) {
        this.content = content
    }

    String getContent() {
        return content
    }

    void setContent(String content) {
        this.content = content
    }
}
