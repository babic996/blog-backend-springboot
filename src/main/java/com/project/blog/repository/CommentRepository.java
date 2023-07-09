package com.project.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.blog.model.Blog;
import com.project.blog.model.Comment;
import com.project.blog.model.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	List<Comment> findAllByIsReported(Boolean isReported);

	List<Comment> findAllByIsReportedAndBlog(Boolean isReported, Blog blog);

	List<Comment> findAllByIsReportedAndBlogAndUser(Boolean isReported, Blog blog, User user);

	List<Comment> findAllByIsReportedAndUser(Boolean isReported, User user);

	List<Comment> findAllByUser(User user);

	List<Comment> findAllByBlog(Blog blog);

	public void deleteByBlog(Blog blog);

	public void deleteByUser(User user);
}
