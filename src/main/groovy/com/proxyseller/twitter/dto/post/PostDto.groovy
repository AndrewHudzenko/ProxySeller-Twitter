package com.proxyseller.twitter.dto.post

import com.proxyseller.twitter.model.comment.Comment
import com.proxyseller.twitter.model.like.Like

import java.time.Instant

class PostDto {
    private String id;
    private String userId;
    private String content;
    private String imageUrl;
    private Instant postedAt;
    private String location;
    private Set<Like> likes = new HashSet<>();
    private Set<Comment> comments = new HashSet<>();

    PostDto() {
    }

    PostDto(String id, String userId, String content, String imageUrl, Instant postedAt, String location, Set<Like> likes, Set<Comment> comments) {
        this.id = id
        this.userId = userId
        this.content = content
        this.imageUrl = imageUrl
        this.postedAt = postedAt
        this.location = location
        this.likes = likes
        this.comments = comments
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

    String getContent() {
        return content
    }

    void setContent(String content) {
        this.content = content
    }

    String getImageUrl() {
        return imageUrl
    }

    void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl
    }

    Instant getPostedAt() {
        return postedAt
    }

    void setPostedAt(Instant postedAt) {
        this.postedAt = postedAt
    }

    String getLocation() {
        return location
    }

    void setLocation(String location) {
        this.location = location
    }

    Set<Like> getLikes() {
        return likes
    }

    void setLikes(Set<Like> likes) {
        this.likes = likes
    }

    Set<Comment> getComments() {
        return comments
    }

    void setComments(Set<Comment> comments) {
        this.comments = comments
    }

    // Add a comment to the post
    void addComment(Comment comment) {
        comments.add(comment)
    }

    // Remove a comment from the post
    void removeComment(Comment comment) {
        comments.remove(comment)
    }

    // Add a like to the post
    void addLike(Like like) {
        likes.add(like)
    }

    // Remove a like from the post
    void removeLike(Like like) {
        likes.remove(like)
    }
}

