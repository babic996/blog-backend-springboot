package com.project.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.dto.CreateBlogLikeTypeDto;
import com.project.blog.dto.UpdateBlogLikeTypeDto;
import com.project.blog.exception.NotFoundException;
import com.project.blog.model.BlogLikeType;
import com.project.blog.service.BlogLikeTypeService;

@RequestMapping("/blog-like-type")
@RestController
public class BlogLikeTypeController {

	@Autowired
	BlogLikeTypeService blogLikeTypeService;

	@GetMapping("/find-by-id")
	public BlogLikeType findById(@RequestParam Integer blogLikeTypeId) throws NotFoundException {

		return blogLikeTypeService.findById(blogLikeTypeId);
	}

	@GetMapping("/find-all")
	public List<BlogLikeType> findAll() throws NotFoundException {

		return blogLikeTypeService.findAll();
	}

	@PostMapping("/save")
	public BlogLikeType save(@RequestBody CreateBlogLikeTypeDto createBlogLikeTypeDto) throws Exception {

		return blogLikeTypeService.save(createBlogLikeTypeDto);
	}

	@PutMapping("/update")
	public BlogLikeType update(@RequestBody UpdateBlogLikeTypeDto updateBlogLikeTypeDto) throws Exception {

		return blogLikeTypeService.update(updateBlogLikeTypeDto);
	}

	@DeleteMapping("/delete")
	public Integer delete(@RequestParam Integer blogLikeTypeId) throws NotFoundException {

		return blogLikeTypeService.delete(blogLikeTypeId);
	}
}
