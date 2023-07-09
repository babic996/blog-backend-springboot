package com.project.blog.model;

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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "blog_like", schema = "project")
public class Like {

	@Id
	@Column(name = "blog_like_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int blogLikeId;

	@ManyToOne
	@JoinColumn(name = "blog_id", nullable = false)
	private Blog blog;

	@ManyToOne
	@JoinColumn(name = "blog_like_type_id", nullable = false)
	private BlogLikeType blogLikeType;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
