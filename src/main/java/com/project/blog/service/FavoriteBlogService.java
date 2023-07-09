package com.project.blog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blog.dto.FavoriteBlogDto;
import com.project.blog.dto.FindAllBlogDto;
import com.project.blog.exception.NotFoundException;
import com.project.blog.model.Blog;
import com.project.blog.model.FavoriteBlog;
import com.project.blog.model.FavoriteBlogKey;
import com.project.blog.model.User;
import com.project.blog.repository.FavoriteBlogRepository;
import com.project.blog.util.SecurityUtils;

@Service
public class FavoriteBlogService {

	@Autowired
	FavoriteBlogRepository favoriteBlogRepository;

	@Autowired
	UserService userService;

	@Autowired
	BlogService blogService;

	@Autowired
	SecurityUtils securityUtils;

	public FavoriteBlog save(FavoriteBlogDto favoriteBlogDto) throws NotFoundException {

		Blog blog = blogService.findById(favoriteBlogDto.getBlogId());
		User user = userService.findById(securityUtils.loggedInUser().getUserId());
		FavoriteBlog favoriteBlog = new FavoriteBlog();
		FavoriteBlogKey favoriteBlogKey = new FavoriteBlogKey(securityUtils.loggedInUser().getUserId(),
				favoriteBlogDto.getBlogId());

		favoriteBlog.setBlog(blog);
		favoriteBlog.setUser(user);
		favoriteBlog.setFavoriteBlogKey(favoriteBlogKey);

		return favoriteBlogRepository.save(favoriteBlog);
	}

	public Integer delete(Integer blogId) throws NotFoundException {

		Blog blog = blogService.findById(blogId);
		User user = userService.findById(securityUtils.loggedInUser().getUserId());
		favoriteBlogRepository.deleteByUserAndBlog(user, blog);

		return blog.getBlogId();
	}

	public List<FindAllBlogDto> findAllByUser(Integer userId) throws NotFoundException {

		User user = userService.findById(userId);
		List<FindAllBlogDto> findAllBlogDtos = new ArrayList<FindAllBlogDto>();

		findAllBlogDtos = favoriteBlogRepository.findAllByUser(user).stream()
				.map(e -> blogService.buildFindAllBlogDtoFromBlog(e.getBlog())).collect(Collectors.toList());

		return findAllBlogDtos;
	}
}
