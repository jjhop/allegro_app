package com.recruit.githubrepositories.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Component;

import com.recruit.githubrepositories.model.GHRepositoryMetadata;

@Component
@RequiredArgsConstructor
@Log4j2
public class GHRepositoryService {

    private final GHRemoteClient remoteClient;

    public GHRepositoryMetadata fetchFor(String user, String repository) {
        log.debug(() -> String.format("[%s] Calling remoteClient.fetchFor(%s, %s);",
                ThreadContext.get("id"), user, repository));
        return remoteClient.fetchFor(user, repository);
    }
}
