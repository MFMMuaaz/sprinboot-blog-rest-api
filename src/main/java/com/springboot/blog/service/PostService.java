package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto savePost(PostDto post);
    List<PostDto> getAllPosts();
}
