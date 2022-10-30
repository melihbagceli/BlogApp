package com.springboot.blogapp.services;

import com.springboot.blogapp.dtos.CommentDto;

public interface CommentService {
    public CommentDto createComment(long postId, CommentDto commentDto);
}
