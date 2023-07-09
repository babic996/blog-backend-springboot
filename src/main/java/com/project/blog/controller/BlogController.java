package com.project.blog.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.dto.ApprovedBlogDto;
import com.project.blog.dto.CreateBlogDto;
import com.project.blog.dto.DetailsBlogDto;
import com.project.blog.dto.FindAllBlogDto;
import com.project.blog.dto.UpdateBlogDto;
import com.project.blog.exception.NotFoundException;
import com.project.blog.model.Blog;
import com.project.blog.service.BlogService;

@RestController
@RequestMapping("/blog")
public class BlogController {

	@Autowired
	BlogService blogService;

	@GetMapping("/find-by-id")
	public Blog findById(@RequestParam Integer blogId) throws NotFoundException {
		return blogService.findById(blogId);
	}

	@GetMapping("/details")
	public DetailsBlogDto blogDetails(@RequestParam Integer blogId) throws NotFoundException {
		return blogService.details(blogId);
	}

	@PostMapping("/save")
	public Blog save(@RequestBody CreateBlogDto createBlogDto) throws Exception {
		return blogService.save(createBlogDto);
	}

	@PutMapping("/update")
	public Blog update(@RequestBody UpdateBlogDto updateBlogDto) throws Exception {
		return blogService.update(updateBlogDto);
	}

	@PutMapping("/approved")
	public Blog approved(@RequestBody ApprovedBlogDto approvedBlogDto) throws Exception {
		return blogService.approved(approvedBlogDto.getBlogId());
	}

	@GetMapping("/find-all")
	public Page<FindAllBlogDto> findAll(@RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(defaultValue = "1000") int pageSize, @RequestParam(required = false) Boolean isApproved,
			@RequestParam(required = false) Integer blogCategoryId, @RequestParam(required = false) Integer userId,
			@RequestParam(required = false) String blogTitle,
			@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") @RequestParam(required = false) LocalDateTime startTimeFrom,
			@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") @RequestParam(required = false) LocalDateTime endTimeTo)
			throws NotFoundException {
		return blogService.findAll(pageNumber, pageSize, isApproved, blogCategoryId, userId, blogTitle, startTimeFrom,
				endTimeTo);
	}

	@DeleteMapping("/delete")
	public Integer delete(@RequestParam Integer blogId) throws NotFoundException {
		return blogService.delete(blogId);
	}
}
