package com.recruit.githubrepositories.api;

import javax.json.Json;
import java.net.ConnectException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.recruit.githubrepositories.model.GHRepositoryMetadata;
import com.recruit.githubrepositories.service.GHRepositoryService;

@RestController
@RequestMapping(
        value = "/repositories",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Log4j2
public class ApiController {

    private final GHRepositoryService repositoryService;

    @ApiOperation("Get repository info")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "owner", value = "Repository owner id",
                    required = true, dataType = "string",
                    paramType = "path", defaultValue = "jjhop"),
            @ApiImplicitParam(
                    name = "repositoryName", value = "Repository name",
                    required = true, dataType = "string",
                    paramType = "path", defaultValue = "dmail")
    })
    @GetMapping("/{owner}/{repositoryName}")
    public GHRepositoryMetadata get(@PathVariable String owner,
                                    @PathVariable String repositoryName) {
        return repositoryService.fetchFor(owner, repositoryName);
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    ResponseEntity<String> handleNotFound(Exception e) {
        return ResponseEntity
                .status(NOT_FOUND)
                .contentType(APPLICATION_JSON)
                .body(Json
                        .createObjectBuilder()
                        .add("errorMessage", "Repository or user not found")
                        .build().toString()
                );
    }

    @ExceptionHandler(ConnectException.class)
    ResponseEntity<String> handleConnectionError(Exception e) {
        return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .contentType(APPLICATION_JSON)
                    .body(Json
                            .createObjectBuilder()
                            .add("errorMessage", "Remote connection error")
                            .build().toString()
                    );
    }
}
