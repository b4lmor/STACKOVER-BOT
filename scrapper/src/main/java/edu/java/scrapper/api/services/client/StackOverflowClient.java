package edu.java.scrapper.api.services.client;

import edu.java.scrapper.api.services.dto.stackoverflow.StackOverflowAnswersResponse;
import edu.java.scrapper.api.services.dto.stackoverflow.StackOverflowUserResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class StackOverflowClient {

    private final WebClient webClient;

    private final String baseUrl;

    public StackOverflowClient() {
        this.webClient = WebClient.builder().build();
        this.baseUrl = "https://api.stackexchange.com/2.3";
    }

    public Mono<StackOverflowAnswersResponse> getUpdates(long questionId) {
        return webClient.method(HttpMethod.GET)
            .uri(String.format(baseUrl + "/questions/%d/answers?site=stackoverflow&filter=withbody", questionId))
            .retrieve()
            .bodyToMono(StackOverflowAnswersResponse.class);
    }

    public String getUserName(long userId) {
        return webClient.method(HttpMethod.GET)
            .uri(String.format(baseUrl + "/users/%d?site=stackoverflow", userId))
            .retrieve()
            .toEntity(StackOverflowUserResponse.class)
            .map(response -> {
                var body = response.getBody();
                return body != null
                    ? body.getItems().getFirst().getName()
                    : "";
            })
            .blockOptional()
            .orElseThrow(() -> new RuntimeException("Can't find SOF user's name!"));
    }

}
