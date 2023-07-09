package com.project.blog.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorMessage {
	private String timestamp;
	private int status;
	private String error;
	private String path;
}
