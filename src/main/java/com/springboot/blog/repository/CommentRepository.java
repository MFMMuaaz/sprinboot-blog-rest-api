package com.springboot.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.blog.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	List<Comment> getCommentsByPostId(long postId);
}
