## Spring WebClient
### 특징

- Spring 5 에 포함
- Spring WebFlux Module 의 한 부분
- Functional Sytel API
- Synchronous 와 Asynchronous Rest API Client
- Asynchronous 가 default 이다.

### 써야하는 이유

- Spring web 에서 제공해주는 RestTemplate은 더이상 Spring 재단에서 개발하지 않음.  ([https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html))
- Thread per request Model 의 한계를 극복하기 위해
    - 예를 들어, WAS 에 2명의 사용자 요청이 있을 경우 각 요청은 다른 스레드에 의해서 처리 될 수 있다.</br>
      이 경우에는 멀티 코어 플랫폼에서 응답 시간의 관점에서는 확실히 이득이 있다.</br>
      동시에 여러 요청이 들어왔을 때 사용가능한  Thread 수 만큼만 Reuqest handler 에 전달되고 사용가능한 Thread 수를 넘게 되면 해당 요청들은 대기상태가 된다.</br>
      (서버내에서 추가로 Thread 를 생성해서 사용해야된다면 더 많은 대기상태가 발생할 수 있다. → 추가적으로, 만약 코어 수 이상으로 thread 를 활용하면 context thread 오버헤드가 발생)</br>
- Reactive Programming 방식을 사용하는 Spring WebFlux 는 동시 처리하는데 있어서 thread 수에 의존하지 않고 특정한 수의 thread 를 이용하여 Async Non-Blocking 방식을 통해</br>
  Thread 사용률을 최대한 활용할 수 있다.

### 동작원리

내부적으로는 이벤트 루프 방식을 구현한 Reactive Stream API([http://www.reactive-streams.org](http://www.reactive-streams.org/)) 를 사용

<img src="/img/1.png" width="500px;">

- 이벤트 큐로부터 이벤트들을 순차적으로 처리하고 알맞은 콜백 함수를 실행

### 주의사항

- WebClient 에서 block() 메서드를 통해 Synchronous 하게 사용가능하다. 하지만 이 때는 Module 에 Spring Web 를 추가하여야 한다.

### 참고

- [https://lob-dev.tistory.com/entry/RestTemplate-Traverson-WebClient-정리하기](https://lob-dev.tistory.com/entry/RestTemplate-Traverson-WebClient-%EC%A0%95%EB%A6%AC%ED%95%98%EA%B8%B0)
- [https://www.youtube.com/watch?v=BSRW1HtNyCo](https://www.youtube.com/watch?v=BSRW1HtNyCo)
- Thread per request VS. Event Loop Model in Spring - [https://velog.io/@jihoson94/EventLoopModelInSpring](https://velog.io/@jihoson94/EventLoopModelInSpring)
- Reactive Streams 이란? - [https://velog.io/@jihoson94/Reactive-Streams](https://velog.io/@jihoson94/Reactive-Streams)
