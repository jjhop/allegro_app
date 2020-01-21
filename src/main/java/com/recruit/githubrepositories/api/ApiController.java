package com.recruit.githubrepositories.api;

import javax.json.Json;
import java.net.ConnectException;
import java.util.UUID;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import static org.springframework.http.HttpStatus.FORBIDDEN;
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
        ThreadContext.put("id", UUID.randomUUID().toString());

        log.error(() -> String.format("[" + ThreadContext.get("id") + "] Calling /repositories/%s/%s", owner, repositoryName));
        return repositoryService.fetchFor(owner, repositoryName);
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    ResponseEntity<String> handleNotFound(Exception ex) {
        log.warn("[" + ThreadContext.get("id") + "] Repository or user not found.");
        return ResponseEntity
                .status(NOT_FOUND)
                .contentType(APPLICATION_JSON)
                .body(Json
                        .createObjectBuilder()
                        .add("errorMessage", "Repository or user not found.")
                        .add("yourRequestId", ThreadContext.get("id"))
                        .build().toString()
                );
    }

    @ExceptionHandler(ConnectException.class)
    ResponseEntity<String> handleConnectionError(Exception ex) {
        log.error("[" + ThreadContext.get("id") + "] Calling external service failed.");
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .contentType(APPLICATION_JSON)
                .body(Json
                        .createObjectBuilder()
                        .add("errorMessage", "Remote connection error")
                        .add("yourRequestId", ThreadContext.get("id"))
                        .build().toString()
                );
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    ResponseEntity<String> handleForbidden(Exception ex) {
        log.warn("[" + ThreadContext.get("id") + "] Request forbidden.", ex.getMessage());
        return ResponseEntity
                .status(FORBIDDEN)
                .contentType(APPLICATION_JSON)
                .body(Json
                        .createObjectBuilder()
                        .add("errorMessage", "Remote connection error: request forbidden")
                        .add("yourRequestId", ThreadContext.get("id"))
                        .build().toString()
                );
    }
}
