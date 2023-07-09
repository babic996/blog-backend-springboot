package com.project.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.dto.FavoriteBlogDto;
import com.project.blog.dto.FindAllBlogDto;
import com.project.blog.exception.NotFoundException;
import com.project.blog.model.FavoriteBlog;
import com.project.blog.service.FavoriteBlogService;

@RequestMapping("/favorite-blog")
@RestController
public class FavoriteBlogController {

	@Autowired
	FavoriteBlogService favoriteBlogService;

	@PostMapping("/save")
	public FavoriteBlog save(@RequestBody FavoriteBlogDto favoriteBlogDto) throws NotFoundException {

		return favoriteBlogService.save(favoriteBlogDto);
	}

	@DeleteMapping("/delete")
	public Integer delete(@RequestParam Integer blogId) throws NotFoundException {

		return favoriteBlogService.delete(blogId);
	}

	@GetMapping("/find-all-by-user")
	public List<FindAllBlogDto> findAllByUser(@RequestParam Integer userId) throws NotFoundException {

		return favoriteBlogService.findAllByUser(userId);
	}

}
