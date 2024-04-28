package com.proxyseller.twitter.service.util

import com.proxyseller.twitter.model.comment.Comment
import com.proxyseller.twitter.model.like.Like
import com.proxyseller.twitter.model.post.Post
import com.proxyseller.twitter.model.user.User

import java.time.Instant

class ModelGeneratorUtil {

    static User generateUser() {
        return new User(
                id: "1",
                firstName: "John",
                lastName: "Doe",
                username: "johndoe",
                password: "encodedPassword",
                email: "johndoe@gmail.com",
                age: 25,
                followers: ["2", "3"],
                following: ["4", "5"],
                isDeleted: false
        )
    }

    static Like generateLike() {
        return new Like(
                id: "1",
                postId: "1",
                userId: "1",
                createdAt: Instant.now()
        )
    }


    static Post generatePost() {
        return new Post(
                id: 1,
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
                ],
                isDeleted: false
        )
    }

    static Comment generateComment() {
        return new Comment(
                id: "1",
                content: "Comment content",
                createdAt: Instant.now(),
                userId: "1",
                postId: 1,
                isDeleted: false
        )
    }
}
