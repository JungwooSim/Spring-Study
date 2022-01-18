package me.study.webclient.external;

import lombok.extern.slf4j.Slf4j;
import me.study.webclient.dto.Follower;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Component
public class GitApi {
    private WebClient webClient;

    @Value("${github.baseUrl}")
    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    /**
     * blocking
     */
    public List<Follower> getSyncFollowers() {
        return webClient.get()
                .uri("/users/JungwooSim/followers")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Follower>>() {})
                .block();
    }

    /**
     * non-blocking
     */
    public Stream<Follower> getAssyncFollwers_Flux() {
        return webClient.get()
                .uri("/users/JungwooSim/followers")
                .retrieve()
                .bodyToFlux(Follower.class)
                .doOnSubscribe(t -> {
                    log.info("-- Start --");
                }).toStream();
    }
}
