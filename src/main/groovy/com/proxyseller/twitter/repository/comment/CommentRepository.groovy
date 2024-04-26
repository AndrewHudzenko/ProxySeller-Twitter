package com.proxyseller.twitter.repository.comment

import com.proxyseller.twitter.model.comment.Comment
import org.springframework.data.mongodb.repository.MongoRepository

interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findAllByPostId(String postId)
    List<Comment> findAllByUserId(String userId)
}