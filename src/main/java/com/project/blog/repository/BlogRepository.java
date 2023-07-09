package com.project.blog.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.blog.model.Blog;
import com.project.blog.model.BlogCategory;
import com.project.blog.model.User;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {

	List<Blog> findAllByUser(User user);

	public void deleteByUser(User user);

	List<Blog> findAllByBlogCategory(BlogCategory blogCategory);

	public void deleteByBlogCategory(BlogCategory blogCategory);

	// @formatter:off
		@Query(value = "SELECT * FROM project.blog WHERE created_at >= ?1 AND created_at <= ?2 "
				+ " ORDER BY blog_id DESC", nativeQuery = true)
		// @formatter:on
	List<Blog> findAllByCreatedAt(LocalDateTime startTimeFrom, LocalDateTime endTimeTo);

	// @formatter:off
	@Query(value = "SELECT * FROM project.blog WHERE created_at >= ?1 AND created_at <= ?2 "
			+ " AND is_approved = ?3 "
			+ "ORDER BY blog_id DESC", nativeQuery = true)
	// @formatter:on
	List<Blog> findAllByCreatedAtAndIsApproved(LocalDateTime startTimeFrom, LocalDateTime endTimeTo,
			Boolean isApproved);

	// @formatter:off
		@Query(value = "SELECT * FROM project.blog WHERE created_at >= ?1 AND created_at <= ?2 "
				+ " AND is_approved = ?3 AND blog_category_id = ?4 "
				+ "ORDER BY blog_id DESC", nativeQuery = true)
		// @formatter:on
	List<Blog> findAllByCreatedAtAndIsApprovedAndBlogCategoryId(LocalDateTime startTimeFrom, LocalDateTime endTimeTo,
			Boolean isApproved, Integer blogCategoryId);

	// @formatter:off
				@Query(value = "SELECT * FROM project.blog WHERE created_at >= ?1 AND created_at <= ?2 "
						+ " AND is_approved = ?3 AND user_id = ?4 "
						+ "ORDER BY blog_id DESC", nativeQuery = true)
				// @formatter:on
	List<Blog> findAllByCreatedAtAndIsApprovedAndUserId(LocalDateTime startTimeFrom, LocalDateTime endTimeTo,
			Boolean isApproved, Integer userId);

	// @formatter:off
				@Query(value = "SELECT * FROM project.blog WHERE created_at >= ?1 AND created_at <= ?2 "
						+ " AND is_approved = ?3 AND (LOWER(blog_title) LIKE LOWER(?4)) "
						+ "ORDER BY blog_id DESC", nativeQuery = true)
				// @formatter:on
	List<Blog> findAllByCreatedAtAndIsApprovedAndBlogTitle(LocalDateTime startTimeFrom, LocalDateTime endTimeTo,
			Boolean isApproved, String blogTitle);

	// @formatter:off
				@Query(value = "SELECT * FROM project.blog WHERE created_at >= ?1 AND created_at <= ?2 "
						+ " AND is_approved = ?3 AND (LOWER(blog_title) LIKE LOWER(?4)) AND blog_category_id = ?5 AND user_id = ?6 "
						+ "ORDER BY blog_id DESC", nativeQuery = true)
				// @formatter:on
	List<Blog> findAllByCreatedAtAndIsApprovedAndBlogTitleAndBlogCategoryIdAndUserId(LocalDateTime startTimeFrom,
			LocalDateTime endTimeTo, Boolean isApproved, String blogTitle, Integer blogCategoryId, Integer userId);

	// @formatter:off
				@Query(value = "SELECT * FROM project.blog WHERE created_at >= ?1 AND created_at <= ?2 "
						+ " AND is_approved = ?3 AND (LOWER(blog_title) LIKE LOWER(?4)) AND blog_category_id = ?5 "
						+ "ORDER BY blog_id DESC", nativeQuery = true)
				// @formatter:on
	List<Blog> findAllByCreatedAtAndIsApprovedAndBlogTitleAndBlogCategoryId(LocalDateTime startTimeFrom,
			LocalDateTime endTimeTo, Boolean isApproved, String blogTitle, Integer blogCategoryId);

	// @formatter:off
				@Query(value = "SELECT * FROM project.blog WHERE created_at >= ?1 AND created_at <= ?2 "
						+ " AND is_approved = ?3 AND (LOWER(blog_title) LIKE LOWER(?4)) AND user_id = ?5 "
						+ "ORDER BY blog_id DESC", nativeQuery = true)
				// @formatter:on
	List<Blog> findAllByCreatedAtAndIsApprovedAndBlogTitleAndUserId(LocalDateTime startTimeFrom,
			LocalDateTime endTimeTo, Boolean isApproved, String blogTitle, Integer userId);

	// @formatter:off
				@Query(value = "SELECT * FROM project.blog WHERE created_at >= ?1 AND created_at <= ?2 "
						+ " AND is_approved = ?3 AND blog_category_id = ?4 AND user_id = ?5 "
						+ "ORDER BY blog_id DESC", nativeQuery = true)
				// @formatter:on
	List<Blog> findAllByCreatedAtAndIsApprovedAndBlogCategoryIdAndUserId(LocalDateTime startTimeFrom,
			LocalDateTime endTimeTo, Boolean isApproved, Integer blogCategoryId, Integer userId);

	// @formatter:off
				@Query(value = "SELECT * FROM project.blog WHERE created_at >= ?1 AND created_at <= ?2 "
						+ " AND blog_category_id = ?3 AND user_id = ?4 "
						+ "ORDER BY blog_id DESC", nativeQuery = true)
				// @formatter:on
	List<Blog> findAllByCreatedAtAndBlogCategoryIdAndUserId(LocalDateTime startTimeFrom, LocalDateTime endTimeTo,
			Integer blogCategoryId, Integer userId);

	// @formatter:off
				@Query(value = "SELECT * FROM project.blog WHERE created_at >= ?1 AND created_at <= ?2 "
						+ " AND user_id = ?3 "
						+ "ORDER BY blog_id DESC", nativeQuery = true)
				// @formatter:on
	List<Blog> findAllByCreatedAtAndUserId(LocalDateTime startTimeFrom, LocalDateTime endTimeTo, Integer userId);

	// @formatter:off
				@Query(value = "SELECT * FROM project.blog WHERE created_at >= ?1 AND created_at <= ?2 "
						+ " AND blog_category_id = ?3 "
						+ "ORDER BY blog_id DESC", nativeQuery = true)
				// @formatter:on
	List<Blog> findAllByCreatedAtAndBlogCategoryId(LocalDateTime startTimeFrom, LocalDateTime endTimeTo,
			Integer blogCategoryId);

	// @formatter:off
				@Query(value = "SELECT * FROM project.blog WHERE created_at >= ?1 AND created_at <= ?2 "
						+ " AND (LOWER(blog_title) LIKE LOWER(?3)) "
						+ "ORDER BY blog_id DESC", nativeQuery = true)
				// @formatter:on
	List<Blog> findAllByCreatedAtAndBlogTitle(LocalDateTime startTimeFrom, LocalDateTime endTimeTo, String blogTitle);

	// @formatter:off
				@Query(value = "SELECT * FROM project.blog WHERE created_at >= ?1 AND created_at <= ?2 "
						+ " AND (LOWER(blog_title) LIKE LOWER(?3)) AND blog_category_id = ?4 AND user_id = ?5 "
						+ "ORDER BY blog_id DESC", nativeQuery = true)
				// @formatter:on
	List<Blog> findAllByCreatedAtAndBlogTitleAndBlogCategoryIdAndUserId(LocalDateTime startTimeFrom,
			LocalDateTime endTimeTo, String blogTitle, Integer blogCategoryId, Integer userId);

}
