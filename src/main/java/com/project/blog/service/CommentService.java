package com.project.blog.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.blog.dto.CommentDetailsDto;
import com.project.blog.dto.CreateCommentDto;
import com.project.blog.dto.UpdateCommentDto;
import com.project.blog.exception.NotFoundException;
import com.project.blog.model.Blog;
import com.project.blog.model.Comment;
import com.project.blog.model.User;
import com.project.blog.repository.BlogRepository;
import com.project.blog.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	UserService userService;

	@Autowired
	BlogRepository blogRepository;

	public Comment findById(Integer commentId) throws NotFoundException {

		return commentRepository.findById(commentId)
				.orElseThrow(() -> new NotFoundException("Nije pronadjen komentar sa ID:" + commentId));
	}

	public Comment save(CreateCommentDto createCommentDto) throws NotFoundException {

		Comment comment = new Comment();
		User user = userService.findById(createCommentDto.getUserId());
		Blog blog = blogRepository.findById(createCommentDto.getBlogId())
				.orElseThrow(() -> new NotFoundException("Nije pronadjen blog sa ID: " + createCommentDto.getBlogId()));

		comment.setCommentText(createCommentDto.getCommentText());
		comment.setUser(user);
		comment.setBlog(blog);

		return commentRepository.save(comment);
	}

	public Comment update(UpdateCommentDto updateCommentDto) throws NotFoundException {

		Comment comment = findById(updateCommentDto.getCommentId());

		comment.setCommentText(Objects.nonNull(updateCommentDto.getCommentText()) ? updateCommentDto.getCommentText()
				: comment.getCommentText());
		comment.setUpdatedAt(LocalDateTime.now());
		commentRepository.save(comment);

		return comment;
	}

	public Integer delete(Integer commentId) throws NotFoundException {

		Comment comment = findById(commentId);
		commentRepository.delete(comment);

		return commentId;
	}

	public Page<CommentDetailsDto> findAll(int pageNumber, int pageSize, Boolean isReported, Integer blogId,
			Integer userId) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		List<CommentDetailsDto> comments = new ArrayList<CommentDetailsDto>();

		Blog blog = new Blog();
		User user = new User();

		if (Objects.nonNull(userId)) {
			user = userService.findById(userId);
		}
		if (Objects.nonNull(blogId)) {
			blog = blogRepository.findById(blogId)
					.orElseThrow(() -> new NotFoundException("Nije pronadjen blog sa ID: " + blogId));
		}

		if (Objects.nonNull(isReported) && Objects.nonNull(blogId) && Objects.nonNull(userId)) {
			comments = commentRepository.findAllByIsReportedAndBlogAndUser(isReported, blog, user).stream()
					.map(e -> buildCommentDetailsDtoFromComment(e)).collect(Collectors.toList());
		} else if (Objects.nonNull(isReported) && Objects.nonNull(blogId) && Objects.isNull(userId)) {
			comments = commentRepository.findAllByIsReportedAndBlog(isReported, blog).stream()
					.map(e -> buildCommentDetailsDtoFromComment(e)).collect(Collectors.toList());
			;
		} else if (Objects.nonNull(isReported) && Objects.nonNull(userId) && Objects.isNull(blogId)) {
			comments = commentRepository.findAllByIsReportedAndUser(isReported, user).stream()
					.map(e -> buildCommentDetailsDtoFromComment(e)).collect(Collectors.toList());
			;
		} else if (Objects.nonNull(isReported) && Objects.isNull(blogId) && Objects.isNull(userId)) {
			comments = commentRepository.findAllByIsReported(isReported).stream()
					.map(e -> buildCommentDetailsDtoFromComment(e)).collect(Collectors.toList());
			;
		} else if (Objects.isNull(isReported) && Objects.isNull(blogId) && Objects.isNull(userId)) {
			comments = commentRepository.findAll().stream().map(e -> buildCommentDetailsDtoFromComment(e))
					.collect(Collectors.toList());
			;
		} else if (Objects.isNull(isReported) && Objects.isNull(blogId) && Objects.nonNull(userId)) {
			comments = commentRepository.findAllByUser(user).stream().map(e -> buildCommentDetailsDtoFromComment(e))
					.collect(Collectors.toList());
			;
		} else if (Objects.isNull(isReported) && Objects.nonNull(blogId) && Objects.isNull(userId)) {
			comments = commentRepository.findAllByBlog(blog).stream().map(e -> buildCommentDetailsDtoFromComment(e))
					.collect(Collectors.toList());
			;
		}

		int start = (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), comments.size());
		if (start > end) {
			start = end = 0;
		}

		final Page<CommentDetailsDto> page = new PageImpl<>(comments.subList(start, end), pageable, comments.size());
		return page;
	}

	public Comment reportComment(Integer commentId) throws NotFoundException {

		Comment comment = findById(commentId);
		comment.setNumberOfReports(comment.getNumberOfReports() + 1);

		if (comment.getNumberOfReports() > 4) {
			comment.setReported(true);
		}

		return commentRepository.save(comment);
	}

	public void deleteByBlog(Blog blog) {

		commentRepository.deleteByBlog(blog);
	}

	public List<Comment> findAllByBlog(Blog blog) {

		return commentRepository.findAllByBlog(blog);
	}

	private CommentDetailsDto buildCommentDetailsDtoFromComment(Comment comment) {

		CommentDetailsDto commentDetailsDto = new CommentDetailsDto();

		commentDetailsDto.setCommentId(comment.getCommentId());
		commentDetailsDto.setCommentText(comment.getCommentText());
		commentDetailsDto.setUserId(comment.getUser().getUserId());
		commentDetailsDto.setUsername(comment.getUser().getUsername());
		commentDetailsDto.setCreatedAt(comment.getCreatedAt());

		return commentDetailsDto;
	}
}
