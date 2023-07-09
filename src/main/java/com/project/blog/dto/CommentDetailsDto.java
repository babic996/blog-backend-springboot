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
public class CommentDetailsDto {

	private int commentId;
	private String commentText;
	private int userId;
	private String username;
	private LocalDateTime createdAt;
}
