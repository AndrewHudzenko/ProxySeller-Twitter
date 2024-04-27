package com.proxyseller.twitter.model.post

import com.proxyseller.twitter.model.comment.Comment
import com.proxyseller.twitter.model.like.Like
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import java.time.Instant

@Document(collection = "posts")
class Post {
    @Id
    private String id;
    private String userId;
    private String content;
    private String imageUrl;
    private Instant postedAt;
    private String location;
    private Set<Like> likes = new HashSet<>();
    private Set<Comment> comments = new HashSet<>();
    private boolean isDeleted = false;

    Post() {
    }

    Post(String id, String userId, String content, String imageUrl, Instant postedAt, String location, Set<Like> likes, Set<Comment> comments, boolean isDeleted) {
        this.id = id
        this.userId = userId
        this.content = content
        this.imageUrl = imageUrl
        this.postedAt = postedAt
        this.location = location
        this.likes = likes
        this.comments = comments
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

    boolean getIsDeleted() {
        return isDeleted
    }

    void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted
    }

    // Add like to post
    void addLike(Like like) {
        likes.add(like)
    }

    // Remove like from post
    void removeLike(Like like) {
        likes.remove(like)
    }

    // Add comment to post
    void addComment(Comment comment) {
        comments.add(comment)
    }

    // Remove comment from post
    void removeComment(Comment comment) {
        comments.remove(comment)
    }
}
