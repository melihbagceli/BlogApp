package com.springboot.blogapp.repositories;

import com.springboot.blogapp.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //Actually no need to include this JpaRepository Includes That
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
