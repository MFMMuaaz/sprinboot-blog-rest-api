package com.springboot.blog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.service.CommentService;

@RestController
@RequestMapping(path = "/posts")
public class CommentController {
	private CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping(path = "/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable long postId, @RequestBody CommentDto commentDto) {
		CommentDto responseBody = commentService.createComment(postId, commentDto);
		return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
	}

	@GetMapping(path = "/{postId}/comments")
	public List<CommentDto> getCommentsByPostId(@PathVariable long postId) {
		return commentService.getCommentsByPostId(postId);
	}

	@GetMapping(path = "/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable long postId, @PathVariable long commentId) {
		return new ResponseEntity<>(commentService.getCommentById(postId, commentId), HttpStatus.OK);
	}

	@PutMapping(path = "/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> updateCommentById(
			@PathVariable long postId, 
			@PathVariable long commentId,
			@RequestBody CommentDto comment) {
		CommentDto updatedComment = commentService.updateCommentById(postId, commentId, comment);
		return new ResponseEntity<>(updatedComment, HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteCommentById(@PathVariable long postId, @PathVariable long commentId){
		commentService.deleteCommentById(postId, commentId);
		return new ResponseEntity<>("Comment deleted!", HttpStatus.OK);
	}
}
