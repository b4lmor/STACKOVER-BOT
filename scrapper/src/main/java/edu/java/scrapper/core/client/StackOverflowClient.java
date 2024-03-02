package edu.java.scrapper.core.client;

import edu.java.scrapper.api.dto.stackoverflow.AnswerDto;
import edu.java.scrapper.api.dto.stackoverflow.StackOverflowAnswersResponse;
import edu.java.scrapper.api.dto.stackoverflow.StackOverflowUserResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class StackOverflowClient {

    private final WebClient webClient;

    private String baseUrl;

    public StackOverflowClient() {
        this.webClient = WebClient.builder().build();
        this.baseUrl = "https://api.stackexchange.com/2.3";
    }

    public Mono<ResponseEntity<List<AnswerDto>>> getUpdates(long questionId) {
        return webClient.method(HttpMethod.GET)
            .uri(String.format(
                baseUrl + "/questions/%d/answers?site=stackoverflow&filter=withbody",
                questionId
            ))
            .retrieve()
            .bodyToMono(StackOverflowAnswersResponse.class)
            .map(response -> {
                List<AnswerDto> answerDtos = response.getItems().stream()
                    .map(item -> new AnswerDto(
                        item.getBody(),
                        item.getOwner().getUserId()
                    ))
                    .collect(Collectors.toList());
                return ResponseEntity.ok(answerDtos);
            });
    }

    public Mono<ResponseEntity<String>> getUserName(long userId) {
        return webClient.method(HttpMethod.GET)
            .uri(String.format(
                baseUrl + "/users/%d?site=stackoverflow",
                userId
            ))
            .retrieve()
            .toEntity(StackOverflowUserResponse.class)
            .map(response -> {
                var body = response.getBody();
                return ResponseEntity.ok(
                    body != null
                        ? body.getItems().getFirst().getName()
                        : ""
                );
            });
    }

}
