package me.study.webclient.service;

import me.study.webclient.dto.Follower;
import me.study.webclient.external.GitApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GitService {

    private final GitApi gitApi;

    @Autowired
    public GitService(GitApi gitApi) {
        this.gitApi = gitApi;
    }

    public List<Follower> getSyncFollower() {
        return gitApi.getSyncFollowers();
    }
}
