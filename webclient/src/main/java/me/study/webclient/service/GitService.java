package me.study.webclient.service;

import me.study.webclient.dto.Follower;
import me.study.webclient.external.GitApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

import static java.lang.Thread.sleep;

@Service
public class GitService {

    private final GitApi gitApi;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    MutiService mutiService;

    @Autowired
    public GitService(GitApi gitApi) {
        this.gitApi = gitApi;
    }

    public List<Follower> getSyncFollower() {
        return gitApi.getSyncFollowers();
    }



    public String a() throws ExecutionException, InterruptedException {
        String url1 = "http://localhost:8080/test1";
        String url2 = "http://localhost:8080/test2";

        System.out.println(LocalDateTime.now());

        // 비동기 API 호출
//        List<CompletableFuture<String>> completableFutures = List.of("http://localhost:8080/test1", "http://localhost:8080/test2").stream().map(a -> {mutiService.callApi(a, "")})
        CompletableFuture<String> response2 = mutiService.callApi(url2, "22"); // 10
        CompletableFuture<String> response1 = mutiService.callApi(url1, "11");// 5

//        List<CompletableFuture<String>> a1 = IntStream.range(0, 2).mapToObj(a -> mutiService.callApi(url2, "a")).toList();

        // 결과가 완료될 때까지 대기
//        a1.stream().forEach(System.out::println);
        System.out.println(response1.get());
        System.out.println(response2.get());
        System.out.println(LocalDateTime.now());

        // 결과 비교 후 반환
        return "aaa";
    }

    private String compareResults(String result1, String result2) {
        // 결과를 비교하는 로직을 여기에 작성합니다.
        // 예제: 결과 문자열의 길이를 비교하여 더 긴 결과를 반환
        return result1.length() > result2.length() ? result1 : result2;
    }

//    @Async
//    public CompletableFuture<String> callApi(String url, String a) {
//        System.out.println("time : " + a + " : " + LocalDateTime.now());
//        return CompletableFuture.completedFuture(webClientBuilder.build().get().uri(url).retrieve().bodyToMono(String.class).block());
//    }
}
