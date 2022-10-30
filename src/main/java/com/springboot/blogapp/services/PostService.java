package com.springboot.blogapp.services;

import com.springboot.blogapp.dtos.PostDto;
import com.springboot.blogapp.dtos.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    List<PostDto> createPost(List<PostDto> postDtoList);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(Long id);
    PostDto updatePostById(long postId, PostDto post);
    PostDto deletePostById(long postId);
}
