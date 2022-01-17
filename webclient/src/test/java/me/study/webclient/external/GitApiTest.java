package me.study.webclient.external;

import me.study.webclient.dto.Follower;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GitApiTest {

    private final GitApi gitApi;

    @Autowired
    public GitApiTest(GitApi gitApi) {
        this.gitApi = gitApi;
    }

    @Test
    void getFollowers() throws InterruptedException {
//        List<Follower> followers = gitApi.getFollowers();
//        followers.forEach(v -> System.out.println(v.getFollowersUrl()));
        gitApi.getFollowers();
    }
}
