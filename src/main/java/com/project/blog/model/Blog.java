package com.project.blog.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blog", schema = "project")
public class Blog {

	@Id
	@Column(name = "blog_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int blogId;

	@Column(name = "blog_title", nullable = false)
	private String blogTitle;

	@Column(name = "blog_text", nullable = false)
	private String blogText;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	@Column(name = "updated_at", nullable = true)
	private LocalDateTime updatedAt;

	@Column(name = "is_approved", nullable = false)
	private boolean isApproved = false;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "blog_category_id", nullable = false)
	private BlogCategory blogCategory;
}
