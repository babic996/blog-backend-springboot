package com.project.blog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.blog.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String username);

	boolean existsByEmail(String email);

	boolean existsByEmailAndUserIdNot(String email, Integer userId);

	boolean existsByUsername(String username);

	boolean existsByUsernameAndUserIdNot(String username, Integer userId);

	List<User> findAllByActive(Boolean active);
}
