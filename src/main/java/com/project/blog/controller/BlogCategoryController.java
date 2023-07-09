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

import com.project.blog.dto.CreateBlogCategoryDto;
import com.project.blog.dto.UpdateBlogCategoryDto;
import com.project.blog.exception.NotFoundException;
import com.project.blog.model.BlogCategory;
import com.project.blog.service.BlogCategoryService;

@RequestMapping("/blog-category")
@RestController
public class BlogCategoryController {

	@Autowired
	BlogCategoryService blogCategoryService;

	@GetMapping("/find-by-id")
	public BlogCategory findById(@RequestParam Integer blogCategoryId) throws NotFoundException {

		return blogCategoryService.findById(blogCategoryId);
	}

	@GetMapping("/find-all")
	public List<BlogCategory> findAll() throws NotFoundException {

		return blogCategoryService.findAll();
	}

	@PostMapping("/save")
	public BlogCategory save(@RequestBody CreateBlogCategoryDto createBlogCategoryDto) throws Exception {

		return blogCategoryService.save(createBlogCategoryDto);
	}

	@PutMapping("/update")
	public BlogCategory save(@RequestBody UpdateBlogCategoryDto updateBlogCategoryDto) throws NotFoundException {

		return blogCategoryService.update(updateBlogCategoryDto);
	}

	@DeleteMapping("/delete")
	public Integer delete(@RequestParam Integer blogCategoryId) throws NotFoundException {

		return blogCategoryService.delete(blogCategoryId);
	}

}
