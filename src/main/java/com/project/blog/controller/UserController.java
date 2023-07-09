package com.project.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.configuration.JwtUtil;
import com.project.blog.configuration.UserDetailsServiceImpl;
import com.project.blog.dto.ActivateDeactivateUserDto;
import com.project.blog.dto.LoginDto;
import com.project.blog.dto.LoginResponseDto;
import com.project.blog.dto.RegisterUserDto;
import com.project.blog.dto.UpdateUserDto;
import com.project.blog.dto.UserInfoDto;
import com.project.blog.exception.NotFoundException;
import com.project.blog.model.User;
import com.project.blog.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	JwtUtil jwtUtil;

	@PostMapping("/register")
	public User save(@RequestBody RegisterUserDto registerUserDto) throws Exception {
		return userService.save(registerUserDto);
	}

	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginDto loginDto) throws Exception {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (BadCredentialsException ex) {
			return new ResponseEntity<String>("Pogrešni kredencijali", HttpStatus.BAD_REQUEST);
		}
		final UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(loginDto.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);

		return new ResponseEntity<LoginResponseDto>(new LoginResponseDto(jwt), HttpStatus.OK);
	}

	@PutMapping("/update")
	public UserInfoDto update(@RequestBody UpdateUserDto updateUserDto) throws NotFoundException {

		return userService.update(updateUserDto);
	}

	@GetMapping("/find-all")
	public Page<UserInfoDto> findAll(@RequestParam(defaultValue = "0") Integer pageNumber,
			@RequestParam(defaultValue = "1000") Integer pageSize, @RequestParam(required = false) Boolean isActive) {

		return userService.findAll(pageNumber, pageSize, isActive);
	}

	@GetMapping("/details")
	public UserInfoDto details(@RequestParam Integer userId) throws NotFoundException {

		return userService.details(userId);
	}

	@PutMapping("/activate")
	public UserInfoDto activate(@RequestBody ActivateDeactivateUserDto activateDeactivateUserDto)
			throws NotFoundException {

		return userService.activate(activateDeactivateUserDto);
	}

	@PutMapping("/deactivate")
	public UserInfoDto deactivate(@RequestBody ActivateDeactivateUserDto activateDeactivateUserDto)
			throws NotFoundException {

		return userService.deactivate(activateDeactivateUserDto);
	}

	@DeleteMapping("/delete")
	public Integer delete(@RequestParam Integer userId) throws NotFoundException {

		return userService.delete(userId);
	}
}
