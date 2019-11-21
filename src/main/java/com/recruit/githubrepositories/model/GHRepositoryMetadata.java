package com.recruit.githubrepositories.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GHRepositoryMetadata {

	private final String fullName;
	private final String description;
	private final String cloneUrl;
	private final Long stars;
	private final String createdAt;

}
