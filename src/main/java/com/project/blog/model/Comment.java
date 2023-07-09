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
@Table(name = "comment", schema = "project")
public class Comment {

	@Id
	@Column(name = "comment_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int commentId;

	@Column(name = "comment_text", nullable = false)
	private String commentText;

	@Column(name = "number_of_report", nullable = false)
	private Integer numberOfReports = 0;

	@Column(name = "is_reported", nullable = false)
	private boolean isReported = false;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	@Column(name = "updated_at", nullable = true)
	private LocalDateTime updatedAt;

	@ManyToOne
	@JoinColumn(name = "blog_id", nullable = false)
	private Blog blog;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
