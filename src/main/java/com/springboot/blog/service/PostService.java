package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto savePost(PostDto post);
    List<PostDto> getAllPosts();
    PostDto getPostById(long id);
    PostDto updatePostyId(long id, PostDto postDto);
    void deletePostById(long id);
}
