package com.project.blog.service;

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

import com.project.blog.dto.CreateLikeDto;
import com.project.blog.dto.LikeDetailsDto;
import com.project.blog.dto.UpdateLikeDto;
import com.project.blog.exception.BadRequestException;
import com.project.blog.exception.NotFoundException;
import com.project.blog.model.Blog;
import com.project.blog.model.BlogLikeType;
import com.project.blog.model.Like;
import com.project.blog.model.User;
import com.project.blog.repository.BlogRepository;
import com.project.blog.repository.LikeRepository;

@Service
public class LikeService {

	@Autowired
	LikeRepository likeRepository;

	@Autowired
	UserService userService;

	@Autowired
	BlogRepository blogRepository;

	@Autowired
	BlogLikeTypeService blogLikeTypeService;

	public Like findById(Integer likeId) throws NotFoundException {

		return likeRepository.findById(likeId)
				.orElseThrow(() -> new NotFoundException("Nije pronadjen like sa ID: " + likeId + "!"));
	}

	public Like save(CreateLikeDto createLikeDto) throws NotFoundException {

		Like like = new Like();
		User user = userService.findById(createLikeDto.getUserId());
		Blog blog = blogRepository.findById(createLikeDto.getBlogId())
				.orElseThrow(() -> new NotFoundException("Nije pronadjen blog sa ID: " + createLikeDto.getBlogId()));
		BlogLikeType blogLikeType = blogLikeTypeService.findById(createLikeDto.getBlogLikeTypeId());

		if (!likeRepository.existsByBlogAndUser(blog, user)) {
			like.setBlog(blog);
			like.setUser(user);
			like.setBlogLikeType(blogLikeType);
		} else {
			throw new BadRequestException("Korisnik je vec lajkovao ovaj blog!");
		}

		return likeRepository.save(like);
	}

	public Like update(UpdateLikeDto updateLikeDto) throws NotFoundException {

		Like like = findById(updateLikeDto.getBlogLikeId());

		if (Objects.nonNull(updateLikeDto.getBlogLikeTypeId())) {
			if (like.getBlogLikeType().getBlogLikeTypeId() == updateLikeDto.getBlogLikeTypeId()) {
				likeRepository.delete(like);
			} else {
				like.setBlogLikeType(blogLikeTypeService.findById(updateLikeDto.getBlogLikeTypeId()));
				likeRepository.save(like);
			}
		} else {
			throw new BadRequestException("Greska!");
		}

		return like;
	}

	public Page<LikeDetailsDto> findAll(int pageNumber, int pageSize, Integer blogId, Integer userId,
			Integer blogLikeTypeId) {

		List<LikeDetailsDto> likes = new ArrayList<LikeDetailsDto>();
		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		User user = new User();
		BlogLikeType blogLikeType = new BlogLikeType();
		Blog blog = new Blog();

		if (Objects.nonNull(blogId)) {
			blog = blogRepository.findById(blogId)
					.orElseThrow(() -> new NotFoundException("Nije pronadjen blog sa ID: " + blogId));
			;
		}
		if (Objects.nonNull(userId)) {
			user = userService.findById(userId);
		}
		if (Objects.nonNull(blogLikeTypeId)) {
			blogLikeType = blogLikeTypeService.findById(blogLikeTypeId);
		}

		if (Objects.nonNull(blogId) && Objects.nonNull(userId) && Objects.nonNull(blogLikeTypeId)) {
			likes = likeRepository.findAllByBlogLikeTypeAndUserAndBlog(blogLikeType, user, blog).stream()
					.map(e -> buildLikeDetailsDtoFromBlogLike(e)).collect(Collectors.toList());
		} else if (Objects.nonNull(blogId) && Objects.nonNull(userId) && Objects.isNull(blogLikeTypeId)) {
			likes = likeRepository.findAllByBlogAndUser(blog, user).stream()
					.map(e -> buildLikeDetailsDtoFromBlogLike(e)).collect(Collectors.toList());
			;
		} else if (Objects.nonNull(blogId) && Objects.isNull(userId) && Objects.nonNull(blogLikeTypeId)) {
			likes = likeRepository.findAllByBlogAndBlogLikeType(blog, blogLikeType).stream()
					.map(e -> buildLikeDetailsDtoFromBlogLike(e)).collect(Collectors.toList());
			;
		} else if (Objects.isNull(blogId) && Objects.nonNull(userId) && Objects.nonNull(blogLikeTypeId)) {
			likes = likeRepository.findAllByBlogLikeTypeAndUser(blogLikeType, user).stream()
					.map(e -> buildLikeDetailsDtoFromBlogLike(e)).collect(Collectors.toList());
			;
		} else if (Objects.isNull(blogId) && Objects.isNull(userId) && Objects.isNull(blogLikeTypeId)) {
			likes = likeRepository.findAll().stream().map(e -> buildLikeDetailsDtoFromBlogLike(e))
					.collect(Collectors.toList());
			;
		} else if (Objects.nonNull(blogId) && Objects.isNull(userId) && Objects.isNull(blogLikeTypeId)) {
			likes = likeRepository.findAllByBlog(blog).stream().map(e -> buildLikeDetailsDtoFromBlogLike(e))
					.collect(Collectors.toList());
			;
		} else if (Objects.isNull(blogId) && Objects.nonNull(userId) && Objects.isNull(blogLikeTypeId)) {
			likes = likeRepository.findAllByUser(user).stream().map(e -> buildLikeDetailsDtoFromBlogLike(e))
					.collect(Collectors.toList());
			;
		} else if (Objects.isNull(blogId) && Objects.isNull(userId) && Objects.nonNull(blogLikeTypeId)) {
			likes = likeRepository.findAllByBlogLikeType(blogLikeType).stream()
					.map(e -> buildLikeDetailsDtoFromBlogLike(e)).collect(Collectors.toList());
			;
		}

		int start = (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), likes.size());
		if (start > end) {
			start = end = 0;
		}
		final Page<LikeDetailsDto> page = new PageImpl<>(likes.subList(start, end), pageable, likes.size());
		return page;
	}

	public void deleteByBlog(Blog blog) {

		likeRepository.deleteByBlog(blog);
	}

	public List<Like> findAllByBlog(Blog blog) {

		return likeRepository.findAllByBlog(blog);
	}

	public Integer delete(Integer likeId) throws NotFoundException {

		Like like = findById(likeId);
		likeRepository.delete(like);

		return likeId;
	}

	private LikeDetailsDto buildLikeDetailsDtoFromBlogLike(Like like) {

		LikeDetailsDto likeDetailsDto = new LikeDetailsDto();

		likeDetailsDto.setBlogLikeId(like.getBlogLikeId());
		likeDetailsDto.setBlogLikeTypeId(like.getBlogLikeType().getBlogLikeTypeId());
		likeDetailsDto.setUserId(like.getUser().getUserId());

		return likeDetailsDto;
	}

}
