package com.springboot.blog.payload;

import java.util.Date;

import lombok.Data;

@Data
public class ErrorDetails {
	private int status;
	private String message;
	private String details;
	private Date timestamp;
}
