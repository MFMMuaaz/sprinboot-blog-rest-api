package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.exception.BlogAppException;
import com.springboot.blog.exception.ResourceNotFountException;
import com.springboot.blog.model.Comment;
import com.springboot.blog.model.Post;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	private PostRepository postRepo;
	private CommentRepository commentRepo;

	public CommentServiceImpl(PostRepository postRepo, CommentRepository commentRepo) {
		this.postRepo = postRepo;
		this.commentRepo = commentRepo;
	}

	private Comment mapToEntity(CommentDto commentDto) {
		Comment comment = new Comment();
		comment.setId(commentDto.getId());
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());
		return comment;
	}

	private CommentDto mapToDTO(Comment comment) {
		CommentDto commentDto = new CommentDto();
		commentDto.setId(comment.getId());
		commentDto.setName(comment.getName());
		commentDto.setEmail(comment.getEmail());
		commentDto.setBody(comment.getBody());
		return commentDto;
	}
	
	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		Comment comment = mapToEntity(commentDto);
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFountException("Post", "id", postId));
		comment.setPost(post);
		Comment createdComment = commentRepo.save(comment);
		return mapToDTO(createdComment);
	}

	@Override
	public List<CommentDto> getCommentsByPostId(long postId) {
		List<Comment> comments = commentRepo.getCommentsByPostId(postId);
		return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentDto getCommentById(long postId, long commentId) {
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFountException("Post", "id", postId));
		Comment comment = commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFountException("Comment", "id", commentId));
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAppException(400, "The ids are mismatching. Please verify and try again!");
		}
		return mapToDTO(comment);
	}

	@Override
	public CommentDto updateCommentById(long postId, long commentId, CommentDto commentDto) {
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFountException("Post", "id", postId));
		Comment comment = commentRepo.findById(commentId).get();
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAppException(400, "The ids are mismatching. Please verify and try again!");
		}
		comment.setBody(commentDto.getBody());
		comment.setEmail(commentDto.getEmail());
		comment.setName(commentDto.getName());
		Comment updatedComment = commentRepo.save(comment);
		return mapToDTO(updatedComment);
	}

	@Override
	public void deleteCommentById(long postId, long commentId) {
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFountException("Post", "id", postId));
		Comment comment = commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFountException("Comment", "id", commentId));
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAppException(400, "The ids are mismatching. Please verify and try again!");
		}
		commentRepo.delete(comment);
	}

}
