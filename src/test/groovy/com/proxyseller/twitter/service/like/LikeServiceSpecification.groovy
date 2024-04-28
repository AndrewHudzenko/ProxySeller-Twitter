package com.proxyseller.twitter.service.like


import com.proxyseller.twitter.mapper.like.LikeMapper
import com.proxyseller.twitter.mapper.post.PostMapper
import com.proxyseller.twitter.repository.like.LikeRepository
import com.proxyseller.twitter.security.SecurityUtil
import com.proxyseller.twitter.service.like.impl.LikeServiceImpl
import com.proxyseller.twitter.service.post.PostService
import com.proxyseller.twitter.service.util.DtoGeneratorUtil
import com.proxyseller.twitter.service.util.ModelGeneratorUtil
import spock.lang.Specification

class LikeServiceSpecification extends Specification {

    def likeRepository = Mock(LikeRepository)
    def postService = Mock(PostService)
    def likeMapper = Mock(LikeMapper)
    def postMapper = Mock(PostMapper)

    def likeService = new LikeServiceImpl(likeRepository, postService, likeMapper, postMapper)

    def "like post should return like dto"() {
        given:
        def likePostRequestDto = DtoGeneratorUtil.generateLikePostRequestDto()
        def like = ModelGeneratorUtil.generateLike()
        def post = ModelGeneratorUtil.generatePost()
        def likeDto = DtoGeneratorUtil.generateLikeDto()

        likeMapper.toModel(_) >> like
        likeMapper.toDto(_) >> likeDto
        postService.getPostModelById(_) >> post
        postMapper.toDto(_) >> _
        postService.updatePost(_) >> _

        when:
        def result = likeService.likePost(likePostRequestDto)

        then:
        1 * likeRepository.save(like)
        1 * postService.updatePost(_)
        result == likeDto
    }

    def "unlike post should delete like"() {
        given:
        GroovySpy(SecurityUtil, global: true)
        SecurityUtil.getCurrentUserId() >> "1"
        def unlikePostRequestDto = DtoGeneratorUtil.generateUnlikePostRequestDto()
        def like = ModelGeneratorUtil.generateLike()
        def post = ModelGeneratorUtil.generatePost()

        likeRepository.findAllByPostIdAndUserId(_, _) >> [like]
        postService.getPostModelById(_) >> post

        when:
        likeService.unlikePost(unlikePostRequestDto)

        then:
        1 * likeRepository.delete(like)
    }

    def "unlike post should throw exception if like not found"() {
        given:
        GroovySpy(SecurityUtil, global: true)
        SecurityUtil.getCurrentUserId() >> "1"
        def unlikePostRequestDto = DtoGeneratorUtil.generateUnlikePostRequestDto()
        def post = ModelGeneratorUtil.generatePost()

        likeRepository.findAllByPostIdAndUserId(_, _) >> []
        postService.getPostModelById(_) >> post

        when:
        likeService.unlikePost(unlikePostRequestDto)

        then:
        0 * likeRepository.delete(_)
        thrown(RuntimeException)
    }

    def "get likes count should return count of likes by post id"() {
        given:
        def id = "1"
        def likes = [ModelGeneratorUtil.generateLike()]
        likeRepository.findByPostId(_) >> likes

        when:
        def result = likeService.getLikesCount(id)

        then:
        result == 1
        1 * likeRepository.findByPostId(id) >> likes
    }

    def "get likes by post id should return list of like dtos"() {
        given:
        def id = "1"
        def post = ModelGeneratorUtil.generatePost()
        def likes = [ModelGeneratorUtil.generateLike()]
        def likeDtos = [DtoGeneratorUtil.generateLikeDto()]

        postService.getPostModelById(_) >> post
        post.getLikes() >> likes
        likeMapper.toDto(_) >> likeDtos[0]

        when:
        def result = likeService.getLikesByPostId(id)

        then:
        result == likeDtos
        1 * postService.getPostModelById(id) >> post
    }
}
