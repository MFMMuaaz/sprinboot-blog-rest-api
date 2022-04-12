package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostPage;

public interface PostService {
    PostDto savePost(PostDto post);
    
    PostPage getAllPosts(int pageNo, int pageSize);
    
    PostDto getPostById(long id);
    
    PostDto updatePostyId(long id, PostDto postDto);
    
    void deletePostById(long id);
}
