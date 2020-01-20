package com.recruit.githubrepositories.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.recruit.githubrepositories.model.GHRepositoryMetadata;

@Component
@RequiredArgsConstructor
@Log4j2
public class GHRemoteClient {

	private final RestTemplate restTemplate;

	@Value("${URL_TEMPLATE}")
	private String urlTemplate;

	public GHRepositoryMetadata fetchFor(String user, String reponame) {
	    log.debug(() -> String.format("[%s] Remote service url: %s", ThreadContext.get("id"), urlTemplate));
		log.debug(() -> String.format("[%s] Fetching remote service with params: user: %s, reponame: %s", ThreadContext.get("id"), user, reponame));
		JsonNode res = restTemplate.getForObject(urlTemplate, JsonNode.class, user, reponame);
		log.debug(() -> String.format("[%s] Remote service responded wiht [%s]", ThreadContext.get("id"), res.toPrettyString()));
		return new GHRepositoryMetadata(
				res.get("full_name").asText(),
				res.get("description").asText(),
				res.get("clone_url").asText(),
				res.get("stargazers_count").asLong(),
				res.get("created_at").asText());
	}
}
