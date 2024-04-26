package com.proxyseller.twitter.service.like.impl

import com.proxyseller.twitter.repository.like.LikeRepository
import com.proxyseller.twitter.service.like.LikeService
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    @Override
    void likePost(UUID postId) {

    }

    @Override
    void unlikePost(UUID postId) {

    }
}
