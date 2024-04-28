package com.proxyseller.twitter.service.comment


import com.proxyseller.twitter.exception.EntityNotFoundException
import com.proxyseller.twitter.mapper.comment.CommentMapper
import com.proxyseller.twitter.mapper.post.PostMapper
import com.proxyseller.twitter.repository.comment.CommentRepository
import com.proxyseller.twitter.repository.post.PostRepository
import com.proxyseller.twitter.service.comment.impl.CommentServiceImpl
import com.proxyseller.twitter.service.post.PostService
import com.proxyseller.twitter.service.util.DtoGeneratorUtil
import com.proxyseller.twitter.service.util.ModelGeneratorUtil
import spock.lang.Specification

class CommentServiceSpecification extends Specification {

    def commentRepository = Mock(CommentRepository)
    def commentMapper = Mock(CommentMapper)
    def postService = Mock(PostService)
    def postRepository = Mock(PostRepository)
    def postMapper = Mock(PostMapper)

    def commentService = new CommentServiceImpl(commentRepository, commentMapper, postService, postRepository, postMapper)

    def "createComment should return commentDto"() {
        given:
        def commentCreateRequestDto = DtoGeneratorUtil.generateCommentCreateRequestDto()
        def post = ModelGeneratorUtil.generatePost()
        def comment = ModelGeneratorUtil.generateComment()
        def postDto = DtoGeneratorUtil.generatePostDto()
        def commentDto = DtoGeneratorUtil.generateCommentDto()

        postRepository.findById(_) >> Optional.of(post)
        commentMapper.toModel(_) >> comment
        commentRepository.insert(_) >> comment
        postMapper.toDto(_) >> postDto
        postService.updatePost(_) >> postDto
        commentMapper.toDto(_) >> commentDto

        when:
        def result = commentService.createComment(commentCreateRequestDto)

        then:
        1 * postRepository.findById(_) >> Optional.of(post)
        1 * commentMapper.toModel(_) >> comment
        1 * commentRepository.insert(_) >> comment
        1 * postMapper.toDto(_) >> postDto
        1 * postService.updatePost(_) >> postDto
        1 * commentMapper.toDto(_) >> commentDto
        result == commentDto
    }

    def "createComment should throw EntityNotFoundException when post not found"() {
        given:
        def commentCreateRequestDto = DtoGeneratorUtil.generateCommentCreateRequestDto()

        postRepository.findById(_) >> Optional.empty()

        when:
        commentService.createComment(commentCreateRequestDto)

        then:
        thrown(EntityNotFoundException)
    }

    def "getCommentsByPostId should return list of commentDto"() {
        given:
        def postId = "1"
        def comments = [ModelGeneratorUtil.generateComment()]
        def commentDtos = [DtoGeneratorUtil.generateCommentDto()]

        commentRepository.findAllByPostIdAndIsDeletedFalse(_) >> comments
        commentMapper.toDto(_) >> commentDtos[0]

        when:
        def result = commentService.getCommentsByPostId(postId)

        then:
        result.size() == 1
        1 * commentRepository.findAllByPostIdAndIsDeletedFalse(_) >> comments
        1 * commentMapper.toDto(_) >> commentDtos[0]
    }

    def "getCommentsByUserId should return list of commentDto"() {
        given:
        def userId = "1"
        def comments = [ModelGeneratorUtil.generateComment()]
        def commentDtos = [DtoGeneratorUtil.generateCommentDto()]

        commentRepository.findAllByUserIdAndIsDeletedFalse(_) >> comments
        commentMapper.toDto(_) >> commentDtos[0]

        when:
        def result = commentService.getCommentsByUserId(userId)

        then:
        result.size() == 1
        1 * commentRepository.findAllByUserIdAndIsDeletedFalse(_) >> comments
        1 * commentMapper.toDto(_) >> commentDtos[0]
    }

    def "getCommentById should return commentDto"() {
        given:
        def id = "1"
        def comment = ModelGeneratorUtil.generateComment()
        def commentDto = DtoGeneratorUtil.generateCommentDto()

        commentRepository.findByIdAndIsDeletedFalse(_) >> Optional.of(comment)
        commentMapper.toDto(_) >> commentDto

        when:
        def result = commentService.getCommentById(id)

        then:
        1 * commentRepository.findByIdAndIsDeletedFalse(_) >> Optional.of(comment)
        1 * commentMapper.toDto(_) >> commentDto
        result == commentDto
    }

    def "getCommentById should throw EntityNotFoundException when comment not found"() {
        given:
        def id = "1"

        commentRepository.findByIdAndIsDeletedFalse(_) >> Optional.empty()

        when:
        commentService.getCommentById(id)

        then:
        thrown(EntityNotFoundException)
    }

    def "deleteComment should delete comment by id"() {
        given:
        def id = "1"
        def comment = ModelGeneratorUtil.generateComment()

        commentRepository.findByIdAndIsDeletedFalse(_) >> Optional.of(comment)

        when:
        commentService.deleteComment(id)

        then:
        1 * commentRepository.findByIdAndIsDeletedFalse(_) >> Optional.of(comment)
        1 * commentRepository.save(_) >> _
    }

    def "deleteComment should throw EntityNotFoundException when comment not found"() {
        given:
        def id = "1"

        commentRepository.findByIdAndIsDeletedFalse(_) >> Optional.empty()

        when:
        commentService.deleteComment(id)

        then:
        thrown(EntityNotFoundException)
    }

    def "updateComment should update comment by id and return commentDto"() {
        given:
        def id = "1"
        def commentUpdateRequestDto = DtoGeneratorUtil.generateCommentUpdateRequestDto()
        def comment = ModelGeneratorUtil.generateComment()
        def commentDto = DtoGeneratorUtil.generateCommentDto()
        def post = ModelGeneratorUtil.generatePost()
        def postDto = DtoGeneratorUtil.generatePostDto()

        commentRepository.findByIdAndIsDeletedFalse(_) >> Optional.of(comment)
        commentRepository.save(_) >> comment
        commentMapper.toDto(_) >> commentDto
        postRepository.findById(_) >> Optional.of(post)
        postMapper.toDto(_) >> postDto
        postService.updatePost(_) >> postDto

        when:
        def result = commentService.updateComment(id, commentUpdateRequestDto)

        then:
        1 * commentRepository.findByIdAndIsDeletedFalse(_) >> Optional.of(comment)
        1 * commentRepository.save(_) >> comment
        2 * commentMapper.toDto(_) >> commentDto
        1 * postRepository.findById(_) >> Optional.of(post)
        1 * postMapper.toDto(_) >> postDto
        1 * postService.updatePost(_) >> postDto
        result == commentDto
    }

    def "updateComment should throw EntityNotFoundException when comment not found"() {
        given:
        def id = "1"
        def commentUpdateRequestDto = DtoGeneratorUtil.generateCommentUpdateRequestDto()

        commentRepository.findByIdAndIsDeletedFalse(_) >> Optional.empty()

        when:
        commentService.updateComment(id, commentUpdateRequestDto)

        then:
        thrown(EntityNotFoundException)
    }

    def "updateComment should throw EntityNotFoundException when post not found"() {
        given:
        def id = "1"
        def commentUpdateRequestDto = DtoGeneratorUtil.generateCommentUpdateRequestDto()
        def comment = ModelGeneratorUtil.generateComment()

        commentRepository.findByIdAndIsDeletedFalse(_) >> Optional.of(comment)
        postRepository.findById(_) >> Optional.empty()

        when:
        commentService.updateComment(id, commentUpdateRequestDto)

        then:
        thrown(EntityNotFoundException)
    }
}
