package com.springboot.blogapp.services.impls;

import com.springboot.blogapp.dtos.PostDto;
import com.springboot.blogapp.dtos.PostResponse;
import com.springboot.blogapp.entities.Post;
import com.springboot.blogapp.exceptions.ResourceNotFoundException;
import com.springboot.blogapp.repositories.PostRepository;
import com.springboot.blogapp.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {


    private PostRepository postRepository;
    private ModelMapper modelMapper;
    @Autowired
    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        //Convert DTO to Entity
        Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post);
        //Convert Entity to PostDto
        PostDto postResponse = mapToDto(post);

        return postResponse;
    }

    @Override
    public List<PostDto> createPost(List<PostDto> postDtoList) {
        List<Post> postList = postDtoList.stream().map(postPdo-> new Post(postPdo.getId(),postPdo.getTitle(),postPdo.getDescription(),postPdo.getContent())).collect(Collectors.toList());
        postRepository.saveAll(postList);
        return postList.stream().map(post-> new PostDto(post.getId(),post.getTitle(),post.getDescription(),post.getContent())).collect(Collectors.toList());
    }


    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        //create Pageable instance
        Sort sort = Sort.by(sortBy);
        sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ? sort.descending() : sort;
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<Post> allPostsPage = postRepository.findAll(pageable);
        List<Post> allPosts = allPostsPage.getContent();

        //List<Post> allPosts = postRepository.findAll();
        List<PostDto> content = allPosts.stream().map(post-> mapToDto(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(allPostsPage.getNumber());
        postResponse.setPageSize(allPostsPage.getSize());
        postResponse.setTotalElements(allPostsPage.getTotalElements());
        postResponse.setTotalPages(allPostsPage.getTotalPages());
        postResponse.setLast(allPostsPage.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePostById(long postId, PostDto postDto) {
        PostDto postDtoInDb = getPostById(postId);
        postDtoInDb.setContent(postDto.getContent());
        postDtoInDb.setTitle(postDto.getTitle());
        postDtoInDb.setDescription(postDto.getDescription());
        Post updatedPost = postRepository.save(mapToEntity(postDtoInDb));
        return mapToDto(updatedPost);
    }

    @Override
    public PostDto deletePostById(long postId) {
        PostDto postDtoInDb = getPostById(postId);
        postRepository.deleteById(postId);
        return postDtoInDb;
    }

    private PostDto mapToDto(Post post){
        PostDto postDto = modelMapper.map(post, PostDto.class);
        //PostDto postDto = new PostDto();
        //postDto.setId(post.getId());
        //postDto.setTitle(post.getTitle());
        //postDto.setContent(post.getContent());
        //postDto.setDescription(post.getDescription());
        return postDto;
    }

    private Post mapToEntity(PostDto postDto){
        Post post = modelMapper.map(postDto, Post.class);
        //Post post = new Post();
        //post.setId(postDto.getId());
        //post.setTitle(postDto.getTitle());
        //post.setDescription(postDto.getDescription());
        //post.setContent(postDto.getContent());
        return post;
    }
}
