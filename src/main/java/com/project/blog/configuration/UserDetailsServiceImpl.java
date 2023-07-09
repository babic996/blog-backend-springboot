package com.project.blog.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.blog.model.User;
import com.project.blog.model.UserRole;
import com.project.blog.repository.UserRepository;
import com.project.blog.repository.UserRoleRepository;

@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	UserRoleRepository userRoleRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException("Nije pronađen korisnik (username:" + username + ")!"));
		List<UserRole> userRoles = userRoleRepository.findAllByUser(user);

		AuthUserDetails auth = new AuthUserDetails(user, userRoles);
		return auth;
	}
}
