package com.recruit.githubrepositories;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import static org.mockito.ArgumentMatchers.any;

import com.recruit.githubrepositories.api.ApiController;
import com.recruit.githubrepositories.service.GHRepositoryService;

@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiControllerTestWithFakeServiceAndWorkingWebCtx {
    @Test
    void shouldReturns404ErrorInfoAsJsonIfRepositoryOrUserDoesntExists() {
        // given
        GHRepositoryService serviceMock = Mockito.mock(GHRepositoryService.class);
        Mockito
                .doThrow(HttpClientErrorException.NotFound.class)
                .when(serviceMock)
                .fetchFor(any(String.class), any(String.class));
        ApiController testedControler = new ApiController(serviceMock);

        // when
//        Object result = testedControler.get("Sss", "sss");

        // then

        // todo

    }
}
