package com.project.blog.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name = "picture", schema = "project")
public class Picture {

	@Id
	@Column(name = "picture_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pictureId;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "picture_source", nullable = false)
	private byte[] pictureSource;

	@Column(name = "picture_name", nullable = false)
	private String pictureName;

	@OneToOne(targetEntity = Blog.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "blog_id")
	private Blog blog;
}
