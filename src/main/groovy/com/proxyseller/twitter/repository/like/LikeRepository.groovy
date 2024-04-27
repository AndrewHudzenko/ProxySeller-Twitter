package com.proxyseller.twitter.repository.like

import com.proxyseller.twitter.model.like.Like
import org.springframework.data.mongodb.repository.MongoRepository

interface LikeRepository extends MongoRepository<Like, UUID> {
    List<Like> findAllByPostIdAndUserId(String postId, String userId)
    List<Like> findByPostId(String id)
}