package com.proxyseller.twitter.repository.comment

import com.proxyseller.twitter.model.comment.Comment
import com.proxyseller.twitter.model.post.Post
import org.springframework.data.mongodb.repository.MongoRepository

interface CommentRepository extends MongoRepository<Comment, String> {
    Optional<Comment> findByIdAndIsDeletedFalse(String id)
    List<Comment> findAllByPostIdAndIsDeletedFalse(String postId)
    List<Comment> findAllByUserIdAndIsDeletedFalse(String userId)
}