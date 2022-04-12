package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostPage;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.Constants;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/posts")
public class PostController {
	private PostService service;

	public PostController(PostService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
		return new ResponseEntity<PostDto>(service.savePost(postDto), HttpStatus.CREATED);
	}

	@GetMapping
	public PostPage getAllPosts(
			@RequestParam(required = false, defaultValue = Constants.DEFAULT_PAGE_NO) int pageNo,
			@RequestParam(required = false, defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize) {
		return service.getAllPosts(pageNo, pageSize);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable long id) {
		PostDto post = service.getPostById(id);
		return new ResponseEntity<>(post, HttpStatus.OK);
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<PostDto> updatePost(@PathVariable long id, @RequestBody PostDto postDto) {
		PostDto updatedPost = service.updatePostyId(id, postDto);
		return new ResponseEntity<>(updatedPost, HttpStatus.OK);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<String> deletePostyId(@PathVariable long id) {
		service.deletePostById(id);
		return new ResponseEntity<>("Post is deleted successflly!", HttpStatus.OK);
	}

}
