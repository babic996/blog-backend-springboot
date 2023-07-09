package com.project.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {

	private String userName;
	private String firstName;
	private String lastName;
	private String adress;
	private String phoneNumber;
	private String email;
	private String password;
}
