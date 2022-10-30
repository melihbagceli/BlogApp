package com.springboot.blogapp.dtos;

import com.springboot.blogapp.entities.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDto {
    private Long id;
    private String name;
    private String email;
    private String body;
}
