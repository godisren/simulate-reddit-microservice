package com.stone.backend.domain;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Post implements Serializable{
	@Id
	private String id;

	@NotNull
	private String content;

	@NotNull
	private String createdBy;

	@CreatedDate
	private Date createdDate;

	public Post() {
		super();
	}

	public Post(String content, String createdBy) {
		super();
		this.content = content;
		this.createdBy = createdBy;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", content=" + content + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ "]";
	}

}
