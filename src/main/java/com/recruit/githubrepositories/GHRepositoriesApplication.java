package com.recruit.githubrepositories;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
public class GHRepositoriesApplication {

	public static void main(String[] args) {
		SpringApplication.run(GHRepositoriesApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}

@Configuration
@EnableSwagger2
class SwaggerConfiguration {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.recruit.githubrepositories"))
				.paths(PathSelectors.ant("/repositories/**"))
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo(
				"RecruitApp REST API",
				"More descriptive description...",
				"API test",
				"Terms of service",
				new Contact("Rafał Kotusiewicz", "https://www.tt.com.pl", "Rafal.Kotusiewicz@tt.com.pl"),
				"License of API", "API license URL", Collections.emptyList());
	}
}

