package com.project.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBlogDto {

	private int blogId;
	private String blogTitle;
	private String blogText;
	private Integer blogCategoryId;
}
