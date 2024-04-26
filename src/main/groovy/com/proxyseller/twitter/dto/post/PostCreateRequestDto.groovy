package com.proxyseller.twitter.dto.post

class PostCreateRequestDto {
    private String imageUrl
    private String content
    private String location

    PostCreateRequestDto() {
    }

    PostCreateRequestDto(String imageUrl, String content, String location) {
        this.imageUrl = imageUrl
        this.content = content
        this.location = location
    }

    String getImageUrl() {
        return imageUrl
    }

    void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl
    }

    String getContent() {
        return content
    }

    void setContent(String content) {
        this.content = content
    }

    String getLocation() {
        return location
    }

    void setLocation(String location) {
        this.location = location
    }
}