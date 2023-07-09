package com.project.blog.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.project.blog.model.User;
import com.project.blog.repository.UserRepository;

@Configuration
public class SecurityUtils {

	@Autowired
	UserRepository userRepository;

	public User loggedInUser() {

		SecurityContext context = SecurityContextHolder.getContext();
		UserDetails userDetails = (UserDetails) context.getAuthentication().getPrincipal();
		User user = userRepository.findByUsername(userDetails.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException(
						"Nije pronađen korisnik (username:" + userDetails.getUsername() + ")!"));
		return user;
	}
}
