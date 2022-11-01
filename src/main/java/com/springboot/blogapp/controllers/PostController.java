package com.springboot.blogapp.controllers;

import com.springboot.blogapp.dtos.PostDto;
import com.springboot.blogapp.services.PostService;
import com.springboot.blogapp.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    //Constructor Oluşturulduğu İçin AutoWired Konulmuyor.
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    // Create Post
    @PostMapping("")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/bulk")
    public ResponseEntity<?> createPostWithList(@RequestBody List<PostDto> postDtoList){
        return new ResponseEntity<>(postService.createPost(postDtoList), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return new ResponseEntity<>(postService.getAllPosts(pageNo,pageSize,sortBy,sortDir),HttpStatus.OK);
    }
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> findPostById(@PathVariable(name = "postId") long postId){
        return new ResponseEntity<>(postService.getPostById(postId), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePostById(@Valid @PathVariable(name = "postId") long postId, @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.updatePostById(postId,postDto), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePostById(@PathVariable(name = "postId") Long postId){
        postService.deletePostById(postId);
        return new ResponseEntity<>("Post Entity Deleted Succesfully",HttpStatus.OK);
    }
}
