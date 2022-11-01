package com.springboot.blogapp.services;

import com.springboot.blogapp.dtos.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(long id);
    CommentDto getCommentById(long postId, long commentId);
    CommentDto updateCommentById(long postId, long commentId, CommentDto commentDto);
    void deleteCommentById(long postId, long commentId);
}
