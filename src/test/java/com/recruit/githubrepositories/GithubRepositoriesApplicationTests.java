package com.recruit.githubrepositories;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class GithubRepositoriesApplicationTests {

	@Autowired
    private TestRestTemplate template;

	@Test
	void shouldReturnProperInfoAboutDmailRepo() {
	    // when
        ResponseEntity<JsonNode> response =
                template.getForEntity("/repositories/{owner}/{repositoryName}", JsonNode.class, "jjhop", "dmail");

        // then
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertTrue(response.hasBody());

        JsonNode responseBody = response.getBody();

        assertEquals("jjhop/dmail", responseBody.get("fullName").asText());
        assertEquals("Desktop email client (clojure@jvm)", responseBody.get("description").asText());
        assertEquals("https://github.com/jjhop/dmail.git", responseBody.get("cloneUrl").asText());
        assertEquals(1, responseBody.get("stars").asInt());
        assertEquals("2018-02-16T23:25:17Z", responseBody.get("createdAt").asText());
	}

	@Test
	void shouldReturn404WhenTryingToGetInfoAboutNonexistingRepo() {
	    String expectedResponseMessage = "Repository or user not found.";

	    // testing for 'nonexistening' as repository name

        ResponseEntity<JsonNode> response =
                template.getForEntity("/repositories/{owner}/{repositoryName}", JsonNode.class, "jjhop", "nonexisting");
        assertTrue(response.getStatusCode().is4xxClientError());
        assertTrue(response.hasBody());

        JsonNode responseBody = response.getBody();
        assertEquals(expectedResponseMessage, responseBody.get("errorMessage").asText());

        // testing for '*' as repository name

        response = template.getForEntity("/repositories/{owner}/{repositoryName}", JsonNode.class, "jjhop", "*");
        assertTrue(response.getStatusCode().is4xxClientError());
        assertTrue(response.hasBody());

        responseBody = response.getBody();
        assertEquals(expectedResponseMessage, responseBody.get("errorMessage").asText());

	}
}
