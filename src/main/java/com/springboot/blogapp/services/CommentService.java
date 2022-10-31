package com.springboot.blogapp.services;

import com.springboot.blogapp.dtos.CommentDto;
import com.springboot.blogapp.entities.Comment;

import java.util.List;

public interface CommentService {
    public CommentDto createComment(long postId, CommentDto commentDto);
    public List<CommentDto> getCommentsByPostId(long id);
}
