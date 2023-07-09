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

import com.project.blog.dto.CommentDetailsDto;
import com.project.blog.dto.CreateCommentDto;
import com.project.blog.dto.ReportCommentDto;
import com.project.blog.dto.UpdateCommentDto;
import com.project.blog.exception.NotFoundException;
import com.project.blog.model.Comment;
import com.project.blog.service.CommentService;

@RequestMapping("/comment")
@RestController
public class CommentController {

	@Autowired
	CommentService commentService;

	@GetMapping("/find-by-id")
	public Comment findById(@RequestParam Integer commentId) throws NotFoundException {

		return commentService.findById(commentId);
	}

	@PostMapping("/save")
	public Comment save(@RequestBody CreateCommentDto createCommentDto) throws NotFoundException {

		return commentService.save(createCommentDto);
	}

	@PutMapping("/update")
	public Comment update(@RequestBody UpdateCommentDto updateCommentDto) throws NotFoundException {

		return commentService.update(updateCommentDto);
	}

	@PutMapping("/report-comment")
	public Comment update(@RequestBody ReportCommentDto reportCommentDto) throws NotFoundException {

		return commentService.reportComment(reportCommentDto.getCommentId());
	}

	public Page<CommentDetailsDto> findAll(@RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(defaultValue = "1000") Integer pageSize, @RequestParam(required = false) Boolean isReported,
			@RequestParam(required = false) Integer blogId, @RequestParam(required = false) Integer userId) {

		return commentService.findAll(pageNumber, pageNumber, isReported, blogId, userId);
	}

	@DeleteMapping("/delete")
	public Integer delete(@RequestParam Integer commentId) throws NotFoundException {

		return commentService.delete(commentId);
	}
}
