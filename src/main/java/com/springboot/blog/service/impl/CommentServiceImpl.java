package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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
	private ModelMapper mapper;

	public CommentServiceImpl(PostRepository postRepo, CommentRepository commentRepo, ModelMapper mapper) {
		this.postRepo = postRepo;
		this.commentRepo = commentRepo;
		this.mapper = mapper;
	}

	private Comment mapToEntity(CommentDto commentDto) {
		return mapper.map(commentDto, Comment.class);
	}

	private CommentDto mapToDTO(Comment comment) {
		return mapper.map(comment, CommentDto.class);
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
