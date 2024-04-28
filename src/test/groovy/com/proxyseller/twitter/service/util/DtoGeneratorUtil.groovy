package com.proxyseller.twitter.service.util

import com.proxyseller.twitter.dto.comment.CommentCreateRequestDto
import com.proxyseller.twitter.dto.comment.CommentDto
import com.proxyseller.twitter.dto.comment.CommentUpdateRequestDto
import com.proxyseller.twitter.dto.like.LikeDto
import com.proxyseller.twitter.dto.like.LikePostRequestDto
import com.proxyseller.twitter.dto.like.UnlikePostRequestDto
import com.proxyseller.twitter.dto.post.PostCreateRequestDto
import com.proxyseller.twitter.dto.post.PostDto
import com.proxyseller.twitter.dto.post.PostUpdateRequestDto
import com.proxyseller.twitter.dto.user.UserDto
import com.proxyseller.twitter.dto.user.UserRegistrationRequestDto
import com.proxyseller.twitter.dto.user.UserUpdateRequestDto

import java.time.Instant

import static com.proxyseller.twitter.service.util.ModelGeneratorUtil.generateComment
import static com.proxyseller.twitter.service.util.ModelGeneratorUtil.generateLike

class DtoGeneratorUtil {

    static UserRegistrationRequestDto generateUserRegistrationRequestDto() {
        return new UserRegistrationRequestDto(
                firstName: "John",
                lastName: "Doe",
                username: "johndoe",
                password: "1234",
                email: "johndoe@gmail.com",
                age: 25
        )
    }

    static UserDto generateUserDto() {
        return new UserDto(
                id: "1",
                username: "johndoe",
                email: "johndoe@gmail.com"
        )
    }

    static UserUpdateRequestDto generateUserUpdateRequestDto() {
        return new UserUpdateRequestDto(
                firstName: "David",
                lastName: "Queen",
                username: "davidqueen",
                email: "davidqueen@gmail.com",
                password: "encodedPassword",
                age: 22
        )
    }

    static LikePostRequestDto generateLikePostRequestDto() {
        return new LikePostRequestDto(
                postId: "1"
        )
    }

    static UnlikePostRequestDto generateUnlikePostRequestDto() {
        return new UnlikePostRequestDto(
                postId: "1"
        )
    }

    static LikeDto generateLikeDto() {
        return new LikeDto(
                id: "1",
                postId: "1",
                userId: "1",
                createdAt: Instant.now()
        )
    }

    static CommentCreateRequestDto generateCommentCreateRequestDto() {
        return new CommentCreateRequestDto(
                content: "Comment content",
                postId: "1"
        )
    }

    static CommentDto generateCommentDto() {
        return new CommentDto(
                id: "1",
                content: "Comment content",
                userId: "1",
                postId: "1"

        )
    }

    static CommentUpdateRequestDto generateCommentUpdateRequestDto() {
        return new CommentUpdateRequestDto(
                content: "Updated comment content"
        )
    }

    static PostCreateRequestDto generatePostCreateRequestDto() {
        return new PostCreateRequestDto(
                imageUrl: "https://image.com",
                content: "Post content",
                location: "Location"
        )
    }

    static PostDto generatePostDto() {
        return new PostDto(
                id: "1",
                userId: "1",
                content: "Post content",
                imageUrl: "https://image.com",
                postedAt: Instant.now(),
                location: "Location",
                likes: [
                        generateLike()
                ],
                comments: [
                        generateComment()
                ]
        )
    }

    static PostUpdateRequestDto generatePostUpdateRequestDto() {
        return new PostUpdateRequestDto(
                imageUrl: "https://updatedimage.com",
                content: "Updated post content",
                location: "Updated location"
        )
    }

}
