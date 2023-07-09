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
@Table(name = "blog_category", schema = "project")
public class BlogCategory {

	@Id
	@Column(name = "blog_category_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int blogCategoryId;

	@Column(name = "category_name", nullable = false)
	private String categoryName;
}
