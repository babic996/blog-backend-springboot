package com.project.blog.service;

import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blog.dto.CreateBlogCategoryDto;
import com.project.blog.dto.UpdateBlogCategoryDto;
import com.project.blog.exception.NotFoundException;
import com.project.blog.model.BlogCategory;
import com.project.blog.repository.BlogCategoryRepository;
import com.project.blog.repository.BlogRepository;

@Service
public class BlogCategoryService {

	@Autowired
	BlogCategoryRepository blogCategoryRepository;

	@Autowired
	BlogRepository blogRepository;

	public BlogCategory findById(Integer blogCategoryId) throws NotFoundException {

		return blogCategoryRepository.findById(blogCategoryId)
				.orElseThrow(() -> new NotFoundException("Nije pronadjena kategorija bloga sa ID: " + blogCategoryId));
	}

	public BlogCategory save(CreateBlogCategoryDto createBlogCategoryDto) throws Exception {

		BlogCategory blogCategory = new BlogCategory();
		blogCategory.setCategoryName(createBlogCategoryDto.getBlogCategoryName());

		return blogCategoryRepository.save(blogCategory);
	}

	public BlogCategory update(UpdateBlogCategoryDto updateBlogCategoryDto) throws NotFoundException {

		BlogCategory blogCategory = findById(updateBlogCategoryDto.getBlogCategoryId());

		blogCategory.setCategoryName(Objects.nonNull(updateBlogCategoryDto.getBlogCategoryName())
				? updateBlogCategoryDto.getBlogCategoryName()
				: blogCategory.getCategoryName());

		return blogCategoryRepository.save(blogCategory);
	}

	@Transactional
	public Integer delete(Integer blogCategoryId) throws NotFoundException {

		BlogCategory blogCategory = findById(blogCategoryId);

		blogRepository.deleteByBlogCategory(blogCategory);
		blogCategoryRepository.delete(blogCategory);

		return blogCategoryId;
	}

	public List<BlogCategory> findAll() throws NotFoundException {

		return blogCategoryRepository.findAll();
	}

}
