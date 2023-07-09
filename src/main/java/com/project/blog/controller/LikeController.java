package com.project.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.dto.CreateLikeDto;
import com.project.blog.dto.LikeDetailsDto;
import com.project.blog.dto.UpdateLikeDto;
import com.project.blog.exception.NotFoundException;
import com.project.blog.model.Like;
import com.project.blog.service.LikeService;

@RequestMapping("/like")
@RestController
public class LikeController {

	@Autowired
	LikeService likeService;

	@PostMapping("/save")
	public Like save(@RequestBody CreateLikeDto createLikeDto) throws NotFoundException {

		return likeService.save(createLikeDto);
	}

	@PutMapping("/update")
	public Like update(@RequestBody UpdateLikeDto updateLikeDto) throws NotFoundException {

		return likeService.update(updateLikeDto);
	}

	@DeleteMapping("/delete")
	public Integer delete(@RequestParam Integer likeId) throws NotFoundException {

		return likeService.delete(likeId);
	}

	@GetMapping("/find-all")
	public Page<LikeDetailsDto> findAll(@RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(defaultValue = "1000") Integer pageSize, @RequestParam(required = false) Integer blogId,
			@RequestParam(required = false) Integer userId, @RequestParam(required = false) Integer blogLikeTypeId) {

		return likeService.findAll(pageNumber, pageNumber, blogId, userId, blogLikeTypeId);
	}

}
