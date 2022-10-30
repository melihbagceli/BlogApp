package com.springboot.blogapp.services.impls;

import com.springboot.blogapp.dtos.CommentDto;
import com.springboot.blogapp.dtos.PostDto;
import com.springboot.blogapp.entities.Comment;
import com.springboot.blogapp.entities.Post;
import com.springboot.blogapp.exceptions.ResourceNotFoundException;
import com.springboot.blogapp.repositories.CommentRepository;
import com.springboot.blogapp.repositories.PostRepository;
import com.springboot.blogapp.services.CommentService;
import com.springboot.blogapp.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);

        Comment commentSave = commentRepository.save(comment);
        return mapToDto(commentSave);
    }

    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setBody(comment.getBody());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setBody(commentDto.getBody());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        return comment;
    }

}
