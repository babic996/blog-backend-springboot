package com.project.blog.dto;

import java.util.List;

import com.project.blog.model.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {

	private int userId;
	private String username;
	private String firstName;
	private String lastName;
	private String adress;
	private String phoneNumber;
	private String email;
	private boolean active;
	private List<Role> roles;

}
