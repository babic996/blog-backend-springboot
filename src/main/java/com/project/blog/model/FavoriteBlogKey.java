package com.project.blog.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class FavoriteBlogKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "user_id", nullable = false)
	private int userId;

	@Column(name = "blog_id", nullable = false)
	private int blogId;
}
