package com.springboot.blog.service.impl;

import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.model.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostPage;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
	private PostRepository repo;
	private ModelMapper mapper;

	public PostServiceImpl(PostRepository repo, ModelMapper mapper) {
		this.repo = repo;
		this.mapper = mapper;
	}

	public PostDto savePost(PostDto postDto) {
		Post post = mapToEntity(postDto);
		Post responsePost = repo.save(post);
		return mapToDTO(responsePost);
	}

	@Override
	public PostPage getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
		Direction dir = sortDir.equalsIgnoreCase(Direction.DESC.toString()) ? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(dir, sortBy));
		Page<Post> page = repo.findAll(pageable);

		PostPage postPage = new PostPage();
		List<PostDto> data = page.getContent().stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
		postPage.setData(data);
		postPage.setPageNo(page.getNumber());
		postPage.setPageSize(page.getSize());
		postPage.setTotalPosts(page.getTotalElements());
		postPage.setTotalPages(page.getTotalPages());
		postPage.setHasMore(page.hasNext());
		return postPage;
	}

	@Override
	public PostDto getPostById(long id) {
		Post post = repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "id", id));
		return mapToDTO(post);
	}

	@Override
	public PostDto updatePostyId(long id, PostDto postDto) {
		Post post = repo.findById(id).get();
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		PostDto updatedPost = mapToDTO(repo.save(post));
		return updatedPost;
	}

	@Override
	public void deletePostById(long id) {
		repo.deleteById(id);
	}

	private Post mapToEntity(PostDto postDto) {
		return mapper.map(postDto, Post.class);
	}

	private PostDto mapToDTO(Post post) {
		return mapper.map(post, PostDto.class);
	}

}
