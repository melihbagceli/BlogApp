package com.springboot.blogapp.controllers;

import com.springboot.blogapp.dtos.CommentDto;
import com.springboot.blogapp.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    CommentService commentService;
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(name = "postId") long postId, @RequestBody CommentDto commentDto){
        CommentDto comments = commentService.createComment(postId,commentDto);
        return new ResponseEntity<>(comments, HttpStatus.CREATED);
    }
}
