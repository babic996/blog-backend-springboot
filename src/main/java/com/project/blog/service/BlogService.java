package com.project.blog.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.blog.dto.CreateBlogDto;
import com.project.blog.dto.DetailsBlogDto;
import com.project.blog.dto.FindAllBlogDto;
import com.project.blog.dto.UpdateBlogDto;
import com.project.blog.exception.NotFoundException;
import com.project.blog.model.Blog;
import com.project.blog.repository.BlogRepository;
import com.project.blog.util.DateTimeUtil;

@Service
public class BlogService {
	@Autowired
	BlogRepository blogRepository;

	@Autowired
	UserService userService;

	@Autowired
	BlogCategoryService blogCategoryService;

	@Autowired
	LikeService likeService;

	@Autowired
	CommentService commentService;

	public Blog findById(Integer blogId) throws NotFoundException {
		return blogRepository.findById(blogId)
				.orElseThrow(() -> new NotFoundException("Nije pronadjen blog sa ID: " + blogId + "!"));
	}

	public Blog save(CreateBlogDto createBlogDto) throws Exception {
		Blog blog = new Blog();

		blog.setBlogText(createBlogDto.getBlogText());
		blog.setBlogTitle(createBlogDto.getBlogTitle());
		blog.setUser(userService.findById(createBlogDto.getUserId()));
		blog.setBlogCategory(blogCategoryService.findById(createBlogDto.getBlogCategoryId()));

		return blogRepository.save(blog);
	}

	public Page<FindAllBlogDto> findAll(int pageNumber, int pageSize, Boolean isApproved, Integer blogCategoryId,
			Integer userId, String blogTitle, LocalDateTime startTimeFrom, LocalDateTime endTimeTo) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		List<Blog> blogs = new ArrayList<Blog>();
		List<FindAllBlogDto> blogDtos = new ArrayList<FindAllBlogDto>();

		if (Objects.isNull(startTimeFrom))
			startTimeFrom = DateTimeUtil.getDateTimeFromString("1999-01-01 00:00:00");

		if (Objects.isNull(endTimeTo)) {
			endTimeTo = LocalDateTime.now();
		}

		if (Objects.nonNull(blogTitle)) {
			blogTitle = "%".concat(blogTitle).concat("%");
		}

		if (Objects.nonNull(isApproved) && Objects.nonNull(blogCategoryId) && Objects.nonNull(userId)
				&& Objects.nonNull(blogTitle)) {
			blogs = blogRepository.findAllByCreatedAtAndIsApprovedAndBlogTitleAndBlogCategoryIdAndUserId(startTimeFrom,
					endTimeTo, isApproved, blogTitle, blogCategoryId, userId);
		} else if (Objects.nonNull(isApproved) && Objects.nonNull(blogCategoryId) && Objects.nonNull(userId)
				&& Objects.isNull(blogTitle)) {
			blogs = blogRepository.findAllByCreatedAtAndIsApprovedAndBlogCategoryIdAndUserId(startTimeFrom, endTimeTo,
					isApproved, blogCategoryId, userId);
		} else if (Objects.nonNull(isApproved) && Objects.nonNull(blogCategoryId) && Objects.isNull(userId)
				&& Objects.nonNull(blogTitle)) {
			blogs = blogRepository.findAllByCreatedAtAndIsApprovedAndBlogTitleAndBlogCategoryId(startTimeFrom,
					endTimeTo, isApproved, blogTitle, blogCategoryId);
		} else if (Objects.nonNull(isApproved) && Objects.isNull(blogCategoryId) && Objects.nonNull(userId)
				&& Objects.nonNull(blogTitle)) {
			blogs = blogRepository.findAllByCreatedAtAndIsApprovedAndBlogTitleAndUserId(startTimeFrom, endTimeTo,
					isApproved, blogTitle, userId);
		} else if (Objects.isNull(isApproved) && Objects.nonNull(blogCategoryId) && Objects.nonNull(userId)
				&& Objects.nonNull(blogTitle)) {
			blogs = blogRepository.findAllByCreatedAtAndBlogTitleAndBlogCategoryIdAndUserId(startTimeFrom, endTimeTo,
					blogTitle, blogCategoryId, userId);
		} else if (Objects.nonNull(isApproved) && Objects.isNull(blogCategoryId) && Objects.isNull(userId)
				&& Objects.nonNull(blogTitle)) {
			blogs = blogRepository.findAllByCreatedAtAndIsApprovedAndBlogTitle(startTimeFrom, endTimeTo, isApproved,
					blogTitle);
		} else if (Objects.nonNull(isApproved) && Objects.nonNull(blogCategoryId) && Objects.isNull(userId)
				&& Objects.isNull(blogTitle)) {
			blogs = blogRepository.findAllByCreatedAtAndIsApprovedAndBlogCategoryId(startTimeFrom, endTimeTo,
					isApproved, blogCategoryId);
		} else if (Objects.nonNull(isApproved) && Objects.isNull(blogCategoryId) && Objects.nonNull(userId)
				&& Objects.isNull(blogTitle)) {
			blogs = blogRepository.findAllByCreatedAtAndIsApprovedAndUserId(startTimeFrom, endTimeTo, isApproved,
					userId);
		} else if (Objects.nonNull(isApproved) && Objects.isNull(blogCategoryId) && Objects.isNull(userId)
				&& Objects.isNull(blogTitle)) {
			blogs = blogRepository.findAllByCreatedAtAndIsApproved(startTimeFrom, endTimeTo, isApproved);
		} else if (Objects.isNull(isApproved) && Objects.nonNull(blogCategoryId) && Objects.isNull(userId)
				&& Objects.isNull(blogTitle)) {
			blogs = blogRepository.findAllByCreatedAtAndBlogCategoryId(startTimeFrom, endTimeTo, blogCategoryId);
		} else if (Objects.isNull(isApproved) && Objects.isNull(blogCategoryId) && Objects.nonNull(userId)
				&& Objects.isNull(blogTitle)) {
			blogs = blogRepository.findAllByCreatedAtAndUserId(startTimeFrom, endTimeTo, userId);
		} else if (Objects.isNull(isApproved) && Objects.isNull(blogCategoryId) && Objects.isNull(userId)
				&& Objects.nonNull(blogTitle)) {
			blogs = blogRepository.findAllByCreatedAtAndBlogTitle(startTimeFrom, endTimeTo, blogTitle);
		} else if (Objects.isNull(isApproved) && Objects.isNull(blogCategoryId) && Objects.isNull(userId)
				&& Objects.isNull(blogTitle)) {
			blogs = blogRepository.findAllByCreatedAt(startTimeFrom, endTimeTo);
		} else if (Objects.isNull(isApproved) && Objects.nonNull(blogCategoryId) && Objects.nonNull(userId)
				&& Objects.isNull(blogTitle)) {
			blogs = blogRepository.findAllByCreatedAtAndBlogCategoryIdAndUserId(startTimeFrom, endTimeTo,
					blogCategoryId, userId);
		}

		if (!blogs.isEmpty()) {
			blogs.forEach((x) -> {
				blogDtos.add(buildFindAllBlogDtoFromBlog(x));
			});
		}

		int start = (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), blogDtos.size());
		if (start > end) {
			start = end = 0;
		}
		final Page<FindAllBlogDto> page = new PageImpl<>(blogDtos.subList(start, end), pageable, blogDtos.size());
		return page;
	}

	public FindAllBlogDto buildFindAllBlogDtoFromBlog(Blog blog) {

		FindAllBlogDto findAllBlogDto = new FindAllBlogDto();

		findAllBlogDto.setBlogId(blog.getBlogId());
		findAllBlogDto.setBlogText(blog.getBlogText());
		findAllBlogDto.setBlogTitle(blog.getBlogTitle());
		findAllBlogDto.setCreatedAt(blog.getCreatedAt());
		findAllBlogDto.setBlogCategoryName(blog.getBlogCategory().getCategoryName());
		findAllBlogDto.setUsername(blog.getUser().getUsername());

		return findAllBlogDto;
	}

	@Transactional
	public Integer delete(Integer blogId) throws NotFoundException {

		Blog blog = findById(blogId);

		likeService.deleteByBlog(blog);
		commentService.deleteByBlog(blog);
		blogRepository.delete(blog);

		return blogId;
	}

	public Blog update(UpdateBlogDto updateBlogDto) throws NotFoundException {

		Blog blog = findById(updateBlogDto.getBlogId());

		blog.setBlogCategory(Objects.nonNull(updateBlogDto.getBlogCategoryId())
				? blogCategoryService.findById(updateBlogDto.getBlogCategoryId())
				: blog.getBlogCategory());
		blog.setBlogText(
				Objects.nonNull(updateBlogDto.getBlogText()) ? updateBlogDto.getBlogText() : blog.getBlogText());
		blog.setBlogTitle(
				Objects.nonNull(updateBlogDto.getBlogTitle()) ? updateBlogDto.getBlogTitle() : blog.getBlogTitle());
		blog.setUpdatedAt(LocalDateTime.now());
		blogRepository.save(blog);

		return blog;

	}

	public Blog approved(Integer blogId) throws NotFoundException {

		Blog blog = findById(blogId);
		blog.setApproved(true);

		return blogRepository.save(blog);
	}

	public DetailsBlogDto details(Integer blogId) throws NotFoundException {

		Blog blog = findById(blogId);
		DetailsBlogDto detailsBlogDto = new DetailsBlogDto();

		detailsBlogDto.setBlogId(blog.getBlogId());
		detailsBlogDto.setBlogCategory(blog.getBlogCategory());
		detailsBlogDto.setBlogText(blog.getBlogText());
		detailsBlogDto.setBlogTitle(blog.getBlogTitle());
		detailsBlogDto.setCreatedAt(blog.getCreatedAt());
		detailsBlogDto.setUpdatedAt(blog.getUpdatedAt());
		detailsBlogDto.setUser(blog.getUser());

		return detailsBlogDto;
	}

}
