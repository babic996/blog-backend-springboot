package com.project.blog.service;

import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blog.dto.CreateBlogLikeTypeDto;
import com.project.blog.dto.UpdateBlogLikeTypeDto;
import com.project.blog.exception.NotFoundException;
import com.project.blog.model.BlogLikeType;
import com.project.blog.repository.BlogLikeTypeRepository;
import com.project.blog.repository.LikeRepository;

@Service
public class BlogLikeTypeService {

	@Autowired
	BlogLikeTypeRepository blogLikeTypeRepository;

	@Autowired
	LikeRepository likeRepository;

	public BlogLikeType findById(Integer blogLikeTypeId) throws NotFoundException {

		return blogLikeTypeRepository.findById(blogLikeTypeId)
				.orElseThrow(() -> new NotFoundException("Nije pronadjen tip lajka sa ID: " + blogLikeTypeId + "!"));
	}

	public BlogLikeType save(CreateBlogLikeTypeDto createBlogLikeTypeDto) throws Exception {

		BlogLikeType blogLikeType = new BlogLikeType();
		blogLikeType.setBlogLikeTypeName(createBlogLikeTypeDto.getBlogLikeTypeName());

		return blogLikeTypeRepository.save(blogLikeType);
	}

	public BlogLikeType update(UpdateBlogLikeTypeDto updateBlogLikeTypeDto) throws NotFoundException {

		BlogLikeType blogLikeType = findById(updateBlogLikeTypeDto.getBlogLikeTypeId());
		blogLikeType.setBlogLikeTypeName(Objects.nonNull(updateBlogLikeTypeDto.getBlogLikeTypeName())
				? updateBlogLikeTypeDto.getBlogLikeTypeName()
				: blogLikeType.getBlogLikeTypeName());

		return blogLikeTypeRepository.save(blogLikeType);
	}

	public List<BlogLikeType> findAll() {
		return blogLikeTypeRepository.findAll();
	}

	@Transactional
	public Integer delete(Integer blogLikeTypeId) throws NotFoundException {

		BlogLikeType blogLikeType = findById(blogLikeTypeId);

		likeRepository.deleteByBlogLikeType(blogLikeType);
		blogLikeTypeRepository.delete(blogLikeType);

		return blogLikeTypeId;
	}
}
