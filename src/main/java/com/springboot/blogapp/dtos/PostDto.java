package com.springboot.blogapp.dtos;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    //Title should not be null or empty
    //title should have at least 2 characters
    @NotEmpty
    @Size(min = 2, message = "Post Title Should have at least 2 characters")
    private String title;
    @NotEmpty
    @Size(min = 10, message = "Post Description Should have at least 10 characters")
    private String description;
    @NotEmpty
    private String content;
    private Set<CommentDto> comments;

    public PostDto(Long id, String title, String description, String content) {
    }
}
