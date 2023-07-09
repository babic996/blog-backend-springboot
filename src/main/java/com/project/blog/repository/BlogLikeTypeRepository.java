package com.project.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.blog.model.BlogLikeType;

@Repository
public interface BlogLikeTypeRepository extends JpaRepository<BlogLikeType, Integer> {

}
