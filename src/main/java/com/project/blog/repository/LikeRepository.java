package com.project.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.blog.model.Blog;
import com.project.blog.model.BlogLikeType;
import com.project.blog.model.Like;
import com.project.blog.model.User;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {

	List<Like> findAllByBlog(Blog blog);

	List<Like> findAllByUser(User user);

	List<Like> findAllByBlogLikeType(BlogLikeType blogLikeType);

	List<Like> findAllByBlogAndUser(Blog blog, User user);

	List<Like> findAllByBlogAndBlogLikeType(Blog blog, BlogLikeType blogLikeType);

	List<Like> findAllByBlogLikeTypeAndUser(BlogLikeType blogLikeType, User user);

	List<Like> findAllByBlogLikeTypeAndUserAndBlog(BlogLikeType blogLikeType, User user, Blog blog);

	public void deleteByBlog(Blog blog);

	public void deleteByUser(User user);

	public void deleteByBlogLikeType(BlogLikeType blogLikeType);

	boolean existsByBlogAndUser(Blog blog, User user);

}
