package com.project.blog.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindAllBlogDto {

	private int blogId;
	private String blogTitle;
	private String blogText;
	private LocalDateTime createdAt;
	private String username;
	private String blogCategoryName;
}
