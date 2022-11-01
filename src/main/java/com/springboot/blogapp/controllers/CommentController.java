package com.springboot.blogapp.controllers;

import com.springboot.blogapp.dtos.CommentDto;
import com.springboot.blogapp.entities.Comment;
import com.springboot.blogapp.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
    CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(name = "postId") long postId, @Valid @RequestBody CommentDto commentDto){
        CommentDto comments = commentService.createComment(postId,commentDto);
        return new ResponseEntity<>(comments, HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getAllComments(@PathVariable(name = "postId") long postId){
        List<CommentDto> comments = commentService.getCommentsByPostId(postId);
        return new ResponseEntity<>(comments,HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(name = "postId") long postId, @PathVariable(name = "commentId") long commentId){
         CommentDto commentDto = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(commentDto,HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable(name = "postId") long postId, @PathVariable(name = "commentId") long commentId, @Valid @RequestBody CommentDto commentDto) {
        CommentDto commentDto1 = commentService.updateCommentById(postId,commentId,commentDto);
        return new ResponseEntity<>(commentDto1,HttpStatus.OK);
    }
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteCommentById(@PathVariable(name = "postId") long postId, @PathVariable(name = "commentId") long commentId){
        commentService.deleteCommentById(postId, commentId);
        return new ResponseEntity<>(String.format("Comment with id %s is deleted.", commentId),HttpStatus.OK);
    }
}
