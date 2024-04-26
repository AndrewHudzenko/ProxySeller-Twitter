package com.proxyseller.twitter.repository.like

import com.proxyseller.twitter.model.like.Like
import org.springframework.data.mongodb.repository.MongoRepository

interface LikeRepository extends MongoRepository<Like, UUID> {
    List<Like> findByPostId(String postId)
    List<Like> findByUserId(String userId)
}