package me.study.webclient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Service
public class MutiService {

  @Autowired
  private WebClient.Builder webClientBuilder;

  @Async
  public CompletableFuture<String> callApi(String url, String a) {
    System.out.println("time : " + a + " : " + LocalDateTime.now());
    return CompletableFuture.completedFuture(webClientBuilder.build().get().uri(url).retrieve().bodyToMono(String.class).block());
  }
}
