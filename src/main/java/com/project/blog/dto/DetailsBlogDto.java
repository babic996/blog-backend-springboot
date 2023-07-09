package com.project.blog.dto;

import java.time.LocalDateTime;

import com.project.blog.model.BlogCategory;
import com.project.blog.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailsBlogDto {

	private int blogId;
	private String blogTitle;
	private String blogText;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private User user;
	private BlogCategory blogCategory;
}
