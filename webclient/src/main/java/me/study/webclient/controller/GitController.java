package me.study.webclient.controller;

import me.study.webclient.dto.Follower;
import me.study.webclient.external.GitApi;
import me.study.webclient.service.GitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
public class GitController {
    private final GitApi gitApi;

    @Autowired
    private GitService gitService;

    @Autowired
    public GitController(GitApi gitApi) {
        this.gitApi = gitApi;
    }

    @GetMapping("/syncFollower")
    public ResponseEntity<List<Follower>> getSyncFollower() throws InterruptedException {
        Thread.sleep(5000);
        List<Follower> result = gitApi.getSyncFollowers();

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/asyncFollwers")
    public ResponseEntity<List<Follower>> getAsSyncFollower() {
        return ResponseEntity.status(HttpStatus.OK).body(gitApi.getAssyncFollwers_Flux().collect(Collectors.toList()));
    }

    @GetMapping("/compare")
    public String compareApis() throws ExecutionException, InterruptedException {
        return gitService.a();
    }

    @GetMapping("/test1")
    public String test() throws ExecutionException, InterruptedException {
        Thread.sleep(5000);
        return "TEST1";
    }

    @GetMapping("/test2")
    public String test2() throws ExecutionException, InterruptedException {
        Thread.sleep(10000);
        return "TEST2";
    }
}
