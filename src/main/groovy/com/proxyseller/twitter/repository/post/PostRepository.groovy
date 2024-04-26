package com.proxyseller.twitter.repository.post

import com.proxyseller.twitter.model.post.Post
import org.springframework.data.mongodb.repository.MongoRepository

interface PostRepository extends MongoRepository<Post, String> {
    Optional<Post> findByIdAndIsDeletedFalse(String id)
    List<Post> findAllByIsDeletedFalse()
    List<Post> findAllByUserIdAndIsDeletedFalse(String userId)
    List<Post> findAllByUserIdInAndIsDeletedFalse(Set<String> userIds)
}