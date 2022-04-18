package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.payload.CommentDto;

public interface CommentService {

	CommentDto createComment(long postId, CommentDto comment);
	
	List<CommentDto> getCommentsByPostId(long postId);
	
	CommentDto getCommentById(long postId, long commentId);
	
	CommentDto updateCommentById(long postId, long commentId, CommentDto comment);
	
	void deleteCommentById(long postId, long commentId);

}
