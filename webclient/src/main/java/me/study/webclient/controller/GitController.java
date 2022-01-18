package me.study.webclient.controller;

import me.study.webclient.dto.Follower;
import me.study.webclient.external.GitApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GitController {
    private final GitApi gitApi;

    @Autowired
    public GitController(GitApi gitApi) {
        this.gitApi = gitApi;
    }

    @GetMapping("/syncFollower")
    public ResponseEntity<List<Follower>> getSyncFollower() {
        List<Follower> result = gitApi.getSyncFollowers();

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/asyncFollwers")
    public ResponseEntity<List<Follower>> getAsSyncFollower() {
        return ResponseEntity.status(HttpStatus.OK).body(gitApi.getAssyncFollwers_Flux().collect(Collectors.toList()));
    }
}
