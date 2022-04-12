package com.springboot.blog.payload;

import java.util.List;

import lombok.Data;

@Data
public class PostPage {
	private List<PostDto> data;
	private int pageNo;
	private int pageSize;
	private long totalPosts;
	private int totalPages;
	private boolean hasMore;
}
