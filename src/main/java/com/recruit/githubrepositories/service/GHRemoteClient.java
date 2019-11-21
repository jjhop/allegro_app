package com.recruit.githubrepositories.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.recruit.githubrepositories.model.GHRepositoryMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class GHRemoteClient {

	private final RestTemplate restTemplate;

	@Value("${URL_TEMPLATE}")
	private String urlTemplate;

	public GHRepositoryMetadata fetchFor(String user, String reponame) {
		JsonNode res = restTemplate.getForObject(urlTemplate, JsonNode.class, user, reponame);
		return new GHRepositoryMetadata(
				res.get("full_name").asText(),
				res.get("description").asText(),
				res.get("clone_url").asText(),
				res.get("stargazers_count").asLong(),
				res.get("created_at").asText());
	}
}
