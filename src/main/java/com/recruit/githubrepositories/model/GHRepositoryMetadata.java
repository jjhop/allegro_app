package com.recruit.githubrepositories.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GHRepositoryMetadata {

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("description")
    private String description;

    @JsonProperty("clone_url")
    private String cloneUrl;

    @JsonProperty("stargazers_count")
    private Long stars;

    @JsonProperty("created_at")
    private String createdAt;

    @Override
    public String toString() {
        return "GHRepositoryMetadata{" +
                "fullName='" + fullName +
                ", description='" + description +
                ", cloneUrl='" + cloneUrl +
                ", stars=" + stars +
                ", createdAt='" + createdAt +
                '}';
    }
}
