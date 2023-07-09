package com.project.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {

	private int userId;
	private String username;
	private String firstName;
	private String password;
	private String lastName;
	private String adress;
	private String phoneNumber;
	private String email;
}
