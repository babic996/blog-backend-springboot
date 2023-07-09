package com.project.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBlogDto {

	private String blogTitle;
	private String blogText;
	private Integer userId;
	private Integer blogCategoryId;
}
