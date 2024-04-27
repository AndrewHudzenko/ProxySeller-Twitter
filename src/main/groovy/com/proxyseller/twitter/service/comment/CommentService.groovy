package com.proxyseller.twitter.service.comment

import com.proxyseller.twitter.dto.comment.CommentCreateRequestDto
import com.proxyseller.twitter.dto.comment.CommentDto
import com.proxyseller.twitter.dto.comment.CommentUpdateRequestDto

interface CommentService {
    /**
     * Method to create a comment
     * @param commentCreateRequestDto
     * @return CommentDto
     */
    CommentDto createComment(CommentCreateRequestDto commentCreateRequestDto)

    /**
     * Method to get all comments by post id
     * @return List<CommentDto>
     * @param postId
     */
    List<CommentDto> getCommentsByPostId(String postId)

    /**
     * Method to get all comments by user id
     * @return List<CommentDto>
     * @param userId
     */
    List<CommentDto> getCommentsByUserId(String userId)

    /**
     * Method to get comment by id
     * @param id
     * @return CommentDto
     */
    CommentDto getCommentById(String id)


    /**
     * Method to delete a comment by id
     * @param id
     */
    void deleteComment(String id)

    /**
     * Method to update a comment by id
     * @param id
     * @param commentUpdateRequestDto
     * @return CommentDto
     */
    CommentDto updateComment(String id, CommentUpdateRequestDto commentUpdateRequestDto)
}