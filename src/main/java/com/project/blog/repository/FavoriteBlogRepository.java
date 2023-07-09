package com.project.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.blog.model.Blog;
import com.project.blog.model.FavoriteBlog;
import com.project.blog.model.User;

@Repository
public interface FavoriteBlogRepository extends JpaRepository<FavoriteBlog, Integer> {

	List<FavoriteBlog> findAllByUser(User user);

	List<FavoriteBlog> findAllByBlog(Blog blog);

	public void deleteByUserAndBlog(User user, Blog blog);

	public void deleteByUser(User user);

}
