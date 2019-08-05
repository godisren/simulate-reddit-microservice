package com.stone.backend.event;

public class CreatingPost {
	private String content;
	private String createdBy;

	public CreatingPost() {
		super();
	}

	public CreatingPost(String content, String createdBy) {
		super();
		this.content = content;
		this.createdBy = createdBy;
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

	@Override
	public String toString() {
		return "CreatingPost [content=" + content + ", createdBy=" + createdBy + "]";
	}

}
