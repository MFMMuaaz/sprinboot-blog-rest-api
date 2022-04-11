package com.springboot.blog.service.impl;

import com.springboot.blog.model.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository repo;

    public PostServiceImpl(PostRepository repo) {
        this.repo = repo;
    }

    public PostDto savePost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post responsePost = repo.save(post);
        return mapToDTO(responsePost);
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = repo.findAll();
        return posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
    }
    
    @Override
	public PostDto getPostById(long id) {
		return mapToDTO(repo.findById(id).get());
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
        Post post = new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

    private PostDto mapToDTO(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }

}
