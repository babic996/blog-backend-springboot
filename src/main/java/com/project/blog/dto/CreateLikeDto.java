package com.project.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateLikeDto {

	private Integer blogId;
	private Integer blogLikeTypeId;
	private Integer userId;
}
