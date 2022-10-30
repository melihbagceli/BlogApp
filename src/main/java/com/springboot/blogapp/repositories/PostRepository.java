package com.springboot.blogapp.repositories;

import com.springboot.blogapp.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>{

}
