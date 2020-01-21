package com.recruit.githubrepositories;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import com.recruit.githubrepositories.api.ApiController;
import com.recruit.githubrepositories.model.GHRepositoryMetadata;
import com.recruit.githubrepositories.service.GHRepositoryService;

@RunWith(JUnit4.class)
class ApiControllerTest {

    @Test
    void shouldReturnsCorrectGHRepositoryMetada() {
        final String fakeUser = "fakeUser";
        final String fakeRepo = "fakeRepo";
        final String faleFullName = fakeUser + "/" + fakeRepo;
        final String fakeDesc = "fakeDescription";
        final Long fakeStarsCount = 2L;
        final String fakeURL = "https://github.com/fakeUser/fakeRepo.git";
        final String fakeDate = "now";
        // given
        GHRepositoryMetadata fakeMetadata =
                new GHRepositoryMetadata(faleFullName, fakeDesc, fakeURL, fakeStarsCount, fakeDate);
        GHRepositoryService serviceMock = Mockito.mock(GHRepositoryService.class);
        Mockito
                .doReturn(fakeMetadata)
                .when(serviceMock)
                .fetchFor(fakeUser, fakeRepo);

        ApiController testedControler = new ApiController(serviceMock);

        // when

        GHRepositoryMetadata result = testedControler.get(fakeUser, fakeRepo);

        // then

        Assert.assertEquals(fakeURL, result.getCloneUrl());
        Assert.assertEquals(fakeDate, result.getCreatedAt());
        Assert.assertEquals(fakeDesc, result.getDescription());
        Assert.assertEquals(faleFullName, result.getFullName());
        Assert.assertEquals(fakeStarsCount, result.getStars());
    }

}
