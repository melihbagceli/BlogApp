package com.springboot.blogapp.services.impls;

import com.springboot.blogapp.dtos.CommentDto;
import com.springboot.blogapp.entities.Comment;
import com.springboot.blogapp.entities.Post;
import com.springboot.blogapp.exceptions.BlogAPIException;
import com.springboot.blogapp.exceptions.ResourceNotFoundException;
import com.springboot.blogapp.repositories.CommentRepository;
import com.springboot.blogapp.repositories.PostRepository;
import com.springboot.blogapp.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);

        Comment commentSave = commentRepository.save(comment);
        return mapToDto(commentSave);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long id) {
        List<Comment> comments = commentRepository.findByPostId(id);
        List<CommentDto> commentsDto = comments.stream().map((comment)-> mapToDto(comment)).collect(Collectors.toList());
        return commentsDto;
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "id", commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment Doesn't Belong to This Post");
        }

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateCommentById(long postId, long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "id", commentId));
        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment Doesn't Belong to This Post");
        }
        comment.setBody(commentDto.getBody());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        Comment commentDb = commentRepository.save(comment);
        return mapToDto(commentDb);
    }

    @Override
    public void deleteCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "id", commentId));
        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment Doesn't Belong to This Post");
        }
        commentRepository.delete(comment);
    }

    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = modelMapper.map(comment,CommentDto.class);
        //CommentDto commentDto = new CommentDto();
        //commentDto.setId(comment.getId());
        //commentDto.setBody(comment.getBody());
        //commentDto.setName(comment.getName());
        //commentDto.setEmail(comment.getEmail());
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = modelMapper.map(commentDto,Comment.class);
        //Comment comment = new Comment();
        //comment.setBody(commentDto.getBody());
        //comment.setName(commentDto.getName());
        //comment.setEmail(commentDto.getEmail());
        return comment;
    }
}
