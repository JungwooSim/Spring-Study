package me.study.webclient.external;

import me.study.webclient.dto.Follower;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
class GitApiTest {

    private final GitApi gitApi;

    @Autowired
    public GitApiTest(GitApi gitApi) {
        this.gitApi = gitApi;
    }

    @Test
    void getSyncFollowers() {
        //when
        List<Follower> followers = gitApi.getSyncFollowers();

        //then
        followers.forEach(o -> System.out.println("siteAdmin : " + o.getLogin()));
    }

    @Test
    void getAnSyncFollowers_success() {
        Stream<Follower> followers = gitApi.getAssyncFollwers_Flux();
        System.out.println("dddddd");

        followers.forEach(follower -> {
            System.out.println("Follower ID : " + follower.getLogin());
        });
    }

    @Test
    void test() throws InterruptedException {

        // Client 설정
        WebClient webClient = WebClient.builder()
                .baseUrl(gitApi.getBaseUrl())
                .clientConnector(
                        new ReactorClientHttpConnector(
                                HttpClient.create()
                                        .headers(entries -> entries
                                                .add("User-Agent", "value"))
                                        .responseTimeout(Duration.ofSeconds(10))
                        )
                )
                .defaultHeader("test-header", "okok")
                .build();

        // subscribe
        webClient.get()
                .uri("/users/JungwooSim/followers")
                .retrieve()
//                .onStatus(HttpStatus::is2xxSuccessful, clientResponse -> {
//                    throw new RuntimeException("error");
//                })
                .bodyToMono(Follower[].class)
                .doOnSuccess(t -> {
                    System.out.println("-- start --");
                    Arrays.stream(t).forEach(follower -> {
                        System.out.println(follower.getLogin());
                    });
                })
                .doOnNext(followers -> {
                    System.out.println("-- end --");
                })
                .timeout(Duration.ofSeconds(10))
                .log()
                .subscribe();

        Thread.sleep(10000);
    }
}
