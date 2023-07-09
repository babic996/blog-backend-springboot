package com.project.blog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.blog.dto.ActivateDeactivateUserDto;
import com.project.blog.dto.RegisterUserDto;
import com.project.blog.dto.UpdateUserDto;
import com.project.blog.dto.UserInfoDto;
import com.project.blog.exception.BadRequestException;
import com.project.blog.exception.NotFoundException;
import com.project.blog.model.Role;
import com.project.blog.model.User;
import com.project.blog.model.UserRole;
import com.project.blog.model.UserRoleKey;
import com.project.blog.repository.BlogRepository;
import com.project.blog.repository.CommentRepository;
import com.project.blog.repository.FavoriteBlogRepository;
import com.project.blog.repository.LikeRepository;
import com.project.blog.repository.RoleRepository;
import com.project.blog.repository.UserRepository;
import com.project.blog.repository.UserRoleRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRoleRepository userRoleRepository;

	@Autowired
	LikeRepository likeRepository;

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	BlogRepository blogRepository;

	@Autowired
	FavoriteBlogRepository favoriteBlogRepository;

	private static final int ROLE_USER_ID = 2;

	public User findById(Integer userId) throws NotFoundException {
		return userRepository.findById(userId)
				.orElseThrow(() -> new NotFoundException("Nije pronadjen korisnik sa ID: " + userId + "!"));
	}

	public User save(RegisterUserDto registerUserDto) throws Exception {

		if (!userRepository.existsByEmail(registerUserDto.getEmail())
				&& !userRepository.existsByUsername(registerUserDto.getUserName())) {
			User user = new User();
			user.setAdress(registerUserDto.getAdress());
			user.setEmail(registerUserDto.getEmail());
			user.setFirstName(registerUserDto.getFirstName());
			user.setLastName(registerUserDto.getLastName());
			user.setPhoneNumber(registerUserDto.getPhoneNumber());
			user.setUsername(registerUserDto.getUserName());
			user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
			userRepository.save(user);

			UserRoleKey key = new UserRoleKey(user.getUserId(), ROLE_USER_ID);
			Role role = roleRepository.findById(ROLE_USER_ID)
					.orElseThrow(() -> new Exception("Nije pronađena rola (roleId: " + ROLE_USER_ID + ")!"));
			userRoleRepository.save(new UserRole(key, user, role));
			return user;
		}
		throw new Exception("E-mail i username moraju biti jedinstveni!");
	}

	public UserInfoDto update(UpdateUserDto updateUserDto) throws NotFoundException {
		User user = findById(updateUserDto.getUserId());

		user.setAdress(updateUserDto.getAdress());
		user.setPhoneNumber(updateUserDto.getPhoneNumber());
		if (Objects.nonNull(updateUserDto.getEmail())) {
			if (!userRepository.existsByEmailAndUserIdNot(updateUserDto.getEmail(), updateUserDto.getUserId())) {
				user.setEmail(updateUserDto.getEmail());
			} else {
				throw new BadRequestException("Email mora biti jedinstven!");
			}
		}
		if (Objects.nonNull(updateUserDto.getUsername())) {
			if (!userRepository.existsByUsernameAndUserIdNot(updateUserDto.getUsername(), updateUserDto.getUserId())) {
				user.setUsername(updateUserDto.getUsername());
			} else {
				throw new BadRequestException("Username mora biti jedinstven!");
			}
		}
		user.setFirstName(
				Objects.nonNull(updateUserDto.getFirstName()) ? updateUserDto.getFirstName() : user.getFirstName());
		user.setLastName(
				Objects.nonNull(updateUserDto.getLastName()) ? updateUserDto.getLastName() : user.getLastName());
		if (Objects.nonNull(updateUserDto.getPassword())) {
			user.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
		}
		userRepository.save(user);

		return buildUserInfoDtoFromUser(user);
	}

	public Page<UserInfoDto> findAll(int pageNumber, int pageSize, Boolean isActive) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		List<UserInfoDto> userInfoDtos = new ArrayList<UserInfoDto>();
		List<User> users = new ArrayList<User>();

		if (Objects.isNull(isActive)) {
			users = userRepository.findAll();
		} else {
			users = userRepository.findAllByActive(isActive);
		}

		users.forEach(x -> {
			userInfoDtos.add(buildUserInfoDtoFromUser(x));
		});

		int start = (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), userInfoDtos.size());
		if (start > end) {
			start = end = 0;
		}

		final Page<UserInfoDto> page = new PageImpl<>(userInfoDtos.subList(start, end), pageable, userInfoDtos.size());
		return page;
	}

	public UserInfoDto details(Integer userId) throws NotFoundException {

		User user = findById(userId);
		UserInfoDto userInfoDto = buildUserInfoDtoFromUser(user);

		return userInfoDto;
	}

	@Transactional
	public Integer delete(Integer userId) throws NotFoundException {
		User user = findById(userId);

		blogRepository.deleteByUser(user);
		commentRepository.deleteByUser(user);
		likeRepository.deleteByUser(user);
		favoriteBlogRepository.deleteByUser(user);
		userRoleRepository.deleteByUser(user);
		userRepository.delete(user);

		return user.getUserId();
	}

	public UserInfoDto activate(ActivateDeactivateUserDto activateDeactivateUserDto) throws NotFoundException {

		User user = findById(activateDeactivateUserDto.getUserId());
		user.setActive(true);
		userRepository.save(user);

		return buildUserInfoDtoFromUser(user);
	}

	public UserInfoDto deactivate(ActivateDeactivateUserDto activateDeactivateUserDto) throws NotFoundException {

		User user = findById(activateDeactivateUserDto.getUserId());
		user.setActive(false);
		userRepository.save(user);

		return buildUserInfoDtoFromUser(user);
	}

	private UserInfoDto buildUserInfoDtoFromUser(User user) {

		UserInfoDto userInfoDto = new UserInfoDto();

		userInfoDto.setAdress(user.getAdress());
		userInfoDto.setEmail(user.getEmail());
		userInfoDto.setFirstName(user.getFirstName());
		userInfoDto.setLastName(user.getLastName());
		userInfoDto.setPhoneNumber(user.getPhoneNumber());
		userInfoDto.setUserId(user.getUserId());
		userInfoDto.setUsername(user.getUsername());
		userInfoDto.setActive(user.isActive());
		userInfoDto.setRoles(
				userRoleRepository.findAllByUser(user).stream().map(e -> e.getRole()).collect(Collectors.toList()));

		return userInfoDto;

	}
}
