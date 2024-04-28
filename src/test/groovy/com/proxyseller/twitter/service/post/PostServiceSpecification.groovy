package com.proxyseller.twitter.service.post

import com.proxyseller.twitter.exception.EntityNotFoundException
import com.proxyseller.twitter.mapper.post.PostMapper
import com.proxyseller.twitter.repository.post.PostRepository
import com.proxyseller.twitter.repository.user.UserRepository
import com.proxyseller.twitter.security.SecurityUtil
import com.proxyseller.twitter.service.post.impl.PostServiceImpl
import com.proxyseller.twitter.service.util.DtoGeneratorUtil
import com.proxyseller.twitter.service.util.ModelGeneratorUtil
import spock.lang.Specification

class PostServiceSpecification extends Specification {

    def postRepository = Mock(PostRepository)
    def postMapper = Mock(PostMapper)
    def userRepository = Mock(UserRepository)

    def postService = new PostServiceImpl(postRepository, postMapper, userRepository)

    def "createPost should create a post and return the postDto"() {
        given:
        def postCreateRequestDto = DtoGeneratorUtil.generatePostCreateRequestDto()
        def post = ModelGeneratorUtil.generatePost()
        def postDto = DtoGeneratorUtil.generatePostDto()

        when:
        postMapper.toModel(_) >> post
        postRepository.insert(_) >> post
        postMapper.toDto(_) >> postDto

        def result = postService.createPost(postCreateRequestDto)

        then:
        1 * postMapper.toModel(_) >> post
        1 * postRepository.insert(_) >> post
        1 * postMapper.toDto(_) >> postDto

        result == postDto
    }

    def "getPostById should return the postDto"() {
        given:
        def postId = "1"
        def post = ModelGeneratorUtil.generatePost()
        def postDto = DtoGeneratorUtil.generatePostDto()

        when:
        postRepository.findByIdAndIsDeletedFalse(_) >> Optional.of(post)
        postMapper.toDto(_) >> postDto

        def result = postService.getPostById(postId)

        then:
        1 * postRepository.findByIdAndIsDeletedFalse(_) >> Optional.of(post)
        1 * postMapper.toDto(_) >> postDto

        result == postDto
    }

    def "getPostById should throw EntityNotFoundException when post not found"() {
        given:
        def postId = "1"
        postRepository.findByIdAndIsDeletedFalse(_) >> Optional.empty()

        when:
        postService.getPostById(postId)

        then:
        def exception = thrown(EntityNotFoundException)
        exception.message == "Post not found!"
    }

    def "getAllPosts should return a list of postDtos"() {
        given:
        def post = ModelGeneratorUtil.generatePost()
        def postDto = DtoGeneratorUtil.generatePostDto()

        when:
        postRepository.findAllByIsDeletedFalse() >> [post]
        postMapper.toDto(_) >> postDto

        def result = postService.getAllPosts()

        then:
        1 * postRepository.findAllByIsDeletedFalse() >> [post]
        1 * postMapper.toDto(_) >> postDto

        result == [postDto]
    }

    def "getAllPostsByCurrentUser should return a list of postDtos"() {
        given:
        GroovySpy(SecurityUtil, global: true)
        SecurityUtil.getCurrentUserId() >> "1"
        def post = ModelGeneratorUtil.generatePost()
        def postDto = DtoGeneratorUtil.generatePostDto()

        when:
        postRepository.findAllByUserIdAndIsDeletedFalse(_) >> [post]
        postMapper.toDto(_) >> postDto

        def result = postService.getAllPostsByCurrentUser()

        then:
        1 * postRepository.findAllByUserIdAndIsDeletedFalse(_) >> [post]
        1 * postMapper.toDto(_) >> postDto

        result == [postDto]
    }

    def "updatePost should update the post and return the postDto"() {
        given:
        def postDto = DtoGeneratorUtil.generatePostDto()
        def post = ModelGeneratorUtil.generatePost()

        when:
        postMapper.toModel(_) >> post
        postRepository.save(_) >> post
        postMapper.toDto(_) >> postDto

        def result = postService.updatePost(postDto)

        then:
        1 * postMapper.toModel(_) >> post
        1 * postRepository.save(_) >> post
        1 * postMapper.toDto(_) >> postDto

        result == postDto
    }

    def "updatePost should update the post by id and return the postDto"() {
        given:
        def postId = "1"
        def postUpdateRequestDto = DtoGeneratorUtil.generatePostUpdateRequestDto()
        def post = ModelGeneratorUtil.generatePost()
        def postDto = DtoGeneratorUtil.generatePostDto()

        when:
        postRepository.findByIdAndIsDeletedFalse(_) >> Optional.of(post)
        postRepository.save(_) >> post
        postMapper.toDto(_) >> postDto

        def result = postService.updatePost(postId, postUpdateRequestDto)

        then:
        1 * postRepository.findByIdAndIsDeletedFalse(_) >> Optional.of(post)
        1 * postRepository.save(_) >> post
        1 * postMapper.toDto(_) >> postDto

        result == postDto
    }

    def "updatePost should throw EntityNotFoundException when post not found"() {
        given:
        def postId = "1"
        def postUpdateRequestDto = DtoGeneratorUtil.generatePostUpdateRequestDto()
        postRepository.findByIdAndIsDeletedFalse(_) >> Optional.empty()

        when:
        postService.updatePost(postId, postUpdateRequestDto)

        then:
        def exception = thrown(EntityNotFoundException)
        exception.message == "Post not found!"
    }

    def "deletePostById should delete the post"() {
        given:
        def postId = "1"
        def post = ModelGeneratorUtil.generatePost()

        when:
        postRepository.findByIdAndIsDeletedFalse(_) >> Optional.of(post)
        postRepository.save(_) >> post

        postService.deletePostById(postId)

        then:
        1 * postRepository.findByIdAndIsDeletedFalse(_) >> Optional.of(post)
        1 * postRepository.save(_) >> post

        post.isDeleted
    }

    def "deletePostById should throw EntityNotFoundException when post not found"() {
        given:
        def postId = "1"
        postRepository.findByIdAndIsDeletedFalse(_) >> Optional.empty()

        when:
        postService.deletePostById(postId)

        then:
        def exception = thrown(EntityNotFoundException)
        exception.message == "Post not found!"
    }

    def "getAllPostsByUserId should return a list of postDtos"() {
        given:
        def userId = "1"
        def post = ModelGeneratorUtil.generatePost()
        def postDto = DtoGeneratorUtil.generatePostDto()

        when:
        postRepository.findAllByUserIdAndIsDeletedFalse(_) >> [post]
        postMapper.toDto(_) >> postDto

        def result = postService.getAllPostsByUserId(userId)

        then:
        1 * postRepository.findAllByUserIdAndIsDeletedFalse(_) >> [post]
        1 * postMapper.toDto(_) >> postDto

        result == [postDto]
    }

    def "getAllPostsByUserId should return an empty list when no posts found"() {
        given:
        def userId = "1"
        postRepository.findAllByUserIdAndIsDeletedFalse(_) >> []

        when:
        def result = postService.getAllPostsByUserId(userId)

        then:
        1 * postRepository.findAllByUserIdAndIsDeletedFalse(_) >> []
        result == []
    }

    def "getAllPostsByFollowing should return a list of postDtos"() {
        given:
        GroovySpy(SecurityUtil, global: true)
        SecurityUtil.getCurrentUserId() >> "1"
        def post = ModelGeneratorUtil.generatePost()
        def postDto = DtoGeneratorUtil.generatePostDto()

        when:
        userRepository.findByIdAndIsDeletedFalse(_) >> Optional.of(ModelGeneratorUtil.generateUser())
        postRepository.findAllByUserIdInAndIsDeletedFalse(_) >> [post]
        postMapper.toDto(_) >> postDto

        def result = postService.getAllPostsByFollowingUsers()

        then:
        1 * userRepository.findByIdAndIsDeletedFalse(_) >> Optional.of(ModelGeneratorUtil.generateUser())
        1 * postRepository.findAllByUserIdInAndIsDeletedFalse(_) >> [post]
        1 * postMapper.toDto(_) >> postDto

        result == [postDto]
    }

    def "getAllPostsByFollowing should throw EntityNotFoundException when user not found"() {
        given:
        GroovySpy(SecurityUtil, global: true)
        SecurityUtil.getCurrentUserId() >> "1"
        userRepository.findByIdAndIsDeletedFalse(_) >> Optional.empty()

        when:
        postService.getAllPostsByFollowingUsers()

        then:
        def exception = thrown(EntityNotFoundException)
        exception.message == "User not found!"
    }

    def "getAllPostsByFollowing should return an empty list when no posts found"() {
        given:
        GroovySpy(SecurityUtil, global: true)
        SecurityUtil.getCurrentUserId() >> "1"
        userRepository.findByIdAndIsDeletedFalse(_) >> Optional.of(ModelGeneratorUtil.generateUser())
        postRepository.findAllByUserIdInAndIsDeletedFalse(_) >> []

        when:
        def result = postService.getAllPostsByFollowingUsers()

        then:
        1 * userRepository.findByIdAndIsDeletedFalse(_) >> Optional.of(ModelGeneratorUtil.generateUser())
        1 * postRepository.findAllByUserIdInAndIsDeletedFalse(_) >> []

        result == []
    }

    def "getPostModelById should return the post model"() {
        given:
        def postId = "1"
        def post = ModelGeneratorUtil.generatePost()

        when:
        postRepository.findByIdAndIsDeletedFalse(_) >> Optional.of(post)

        def result = postService.getPostModelById(postId)

        then:
        1 * postRepository.findByIdAndIsDeletedFalse(_) >> Optional.of(post)

        result == post
    }

    def "getPostModelById should throw EntityNotFoundException when post not found"() {
        given:
        def postId = "1"
        postRepository.findByIdAndIsDeletedFalse(_) >> Optional.empty()

        when:
        postService.getPostModelById(postId)

        then:
        def exception = thrown(EntityNotFoundException)
        exception.message == "Post not found!"
    }
}
