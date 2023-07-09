package com.project.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "blog_like_type", schema = "project")
public class BlogLikeType {

	@Id
	@Column(name = "blog_like_type_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int blogLikeTypeId;

	@Column(name = "blog_like_type_name", nullable = false)
	private String blogLikeTypeName;
}
