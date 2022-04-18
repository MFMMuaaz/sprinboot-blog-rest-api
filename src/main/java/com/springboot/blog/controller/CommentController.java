package com.springboot.blog.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.model.Post;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import com.springboot.blog.service.PostService;

@RestController
@RequestMapping(path = "/posts")
public class CommentController {
	private CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping(path = "/{postId}/comments")
	public CommentDto createComment(@PathVariable long postId, @RequestBody CommentDto commentDto) {
		return commentService.createComment(postId, commentDto);
	}
	
	@GetMapping(path="/{postId}/comments")
	public List<CommentDto> getCommentsByPostId(@PathVariable long postId){
		return commentService.getCommentsByPostId(postId);
	}
}
