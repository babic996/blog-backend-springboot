package com.project.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.blog.model.BlogCategory;

@Repository
public interface BlogCategoryRepository extends JpaRepository<BlogCategory, Integer> {

}
