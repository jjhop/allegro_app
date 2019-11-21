package com.recruit.githubrepositories.service;

import com.recruit.githubrepositories.model.GHRepositoryMetadata;
import org.springframework.stereotype.Component;

@Component
public class GHRepositoryService {

    private final GHRemoteClient remoteClient;

    public GHRepositoryService(GHRemoteClient remoteClient) {
        this.remoteClient = remoteClient;
    }

    public GHRepositoryMetadata fetchFor(String user, String repository) {
        return remoteClient.fetchFor(user, repository);
    }
}
