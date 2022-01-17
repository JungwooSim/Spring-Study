package me.study.webclient.external;

import lombok.extern.slf4j.Slf4j;
import me.study.webclient.dto.Follower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class GitApi {
    private WebClient webClient;

    @Value("${github.baseUrl}")
    private String baseUrl;

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

        log.info("baseUrl : {}", baseUrl);
    }

    public void getFollowers() throws InterruptedException {
//        webClient.get()
//                .uri("/users/JungwooSim/followers")
//                .retrieve()
//                .bodyToMono(String.class)
//                .doOnSuccess(t -> log.info("follower success"))
//                .doOnError(t -> log.info("error"))
//                .subscribe();
//
//        Thread.sleep(10000);

        String result = webClient.get()
                .uri("/users/JungwooSim/followers")
                .retrieve()
                .bodyToMono(String.class)
                .block();

//        System.out.println(result);
//                .doOnSuccess(t -> log.info("follower success"))
//                .doOnError(t -> log.info("error"))
//                .subscribe();

        /*
        List<Follower> followers = new ArrayList<>();

        try {
            webClient.get()
                    .uri("/users/JungwooSim/followers")
                    .retrieve()
                    .bodyToMono(Follower.class)
                    .doOnSuccess(follower -> {
                        followers.add(new Follower(follower));
                        log.info("Http Success");
//                    followers.add(Arrays.stream(follower).map(Follower::new).collect(Collectors.toList()));
                    })
                    .subscribe();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return followers;
         */
    }
}
