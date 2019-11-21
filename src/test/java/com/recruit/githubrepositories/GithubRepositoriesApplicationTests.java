package com.recruit.githubrepositories;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class GithubRepositoriesApplicationTests {

	@Autowired
	private WebTestClient webClient;

	@Test
	void shouldReturnProperInfoAboutDmailRepo() {
		webClient.get()
				.uri("/repositories/{owner}/{repositoryName}", "jjhop", "dmail")
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody()
				.jsonPath("fullName").isEqualTo("jjhop/dmail")
				.jsonPath("description").isEqualTo("Desktop email client (clojure@jvm)")
				.jsonPath("cloneUrl").isEqualTo("https://github.com/jjhop/dmail.git")
				.jsonPath("stars").isEqualTo(Long.valueOf(1))
				.jsonPath("createdAt").isEqualTo("2018-02-16T23:25:17Z");
	}

	@Test
	void shouldReturn404WhenTryingToGetInfoAboutNonexistingRepo() {
		webClient.get()
				.uri("/repositories/{owner}/{repositoryName}", "jjhop", "nonexisting")
				.exchange()
				.expectStatus().is4xxClientError();
	}
}
