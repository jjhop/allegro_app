package com.recruit.githubrepositories.api;

import com.recruit.githubrepositories.model.GHRepositoryMetadata;
import com.recruit.githubrepositories.service.GHRepositoryService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.net.ConnectException;

@RestController
@RequestMapping("/repositories")
public class ApiController {

    private GHRepositoryService repositoryService;

    public ApiController(GHRepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

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
        return new ResponseEntity<>("Repository or user not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConnectException.class)
    ResponseEntity<String> handleConnectionError(Exception e) {
        return new ResponseEntity<>("Remote connection error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
