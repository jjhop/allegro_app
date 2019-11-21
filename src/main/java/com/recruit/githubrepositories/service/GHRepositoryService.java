package com.recruit.githubrepositories.service;

import com.recruit.githubrepositories.model.GHRepositoryMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GHRepositoryService {

    private final GHRemoteClient remoteClient;

    public GHRepositoryMetadata fetchFor(String user, String repository) {
        return remoteClient.fetchFor(user, repository);
    }
}
