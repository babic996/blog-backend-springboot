package com.project.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blog.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
