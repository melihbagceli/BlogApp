package com.springboot.blogapp.dtos;

import com.springboot.blogapp.entities.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDto {
    private Long id;
    @NotEmpty(message = "Name should not be null or empty")
    private String name;
    @NotEmpty(message = "Email should not be null or empty")
    @Email(message = "Email should be valid.")
    private String email;
    @NotEmpty(message = "Comment body should not be null or empty")
    @Size(min = 10, message = "Comment body should have at least 10 characters")
    private String body;
}
