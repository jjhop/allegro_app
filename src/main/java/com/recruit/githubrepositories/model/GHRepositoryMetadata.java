package com.recruit.githubrepositories.model;

public class GHRepositoryMetadata {

	private final String fullName;
	private final String description;
	private final String cloneUrl;
	private final Long stars;
	private final String createdAt;

	public GHRepositoryMetadata(String fullName, String description, String cloneUrl, Long stars, String createdAt) {
		this.fullName = fullName;
		this.description = description;
		this.cloneUrl = cloneUrl;
		this.stars = stars;
		this.createdAt = createdAt;
	}

	public String getFullName() { return fullName; }
	public String getDescription() { return description; }
	public String getCloneUrl() { return cloneUrl; }
	public Long getStars() { return stars; }
	public String getCreatedAt() { return createdAt; }
}
