package edu.java.scrapper.core.sources;

import edu.java.scrapper.api.dto.stackoverflow.AnswerDto;
import edu.java.scrapper.api.dto.stackoverflow.StackOverflowAnswersResponse;
import edu.java.scrapper.api.dto.stackoverflow.StackOverflowUserResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class StackOverflowService {

    private final WebClient webClient;

    public StackOverflowService() {
        this.webClient = WebClient.builder().build();
    }

    public Mono<ResponseEntity<List<AnswerDto>>> getUpdates(long questionId) {
        return webClient.method(HttpMethod.GET)
            .uri(String.format(
                "https://api.stackexchange.com/2.3/questions/%d/answers?order=desc&sort=activity&site=stackoverflow&filter=withbody",
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
                "https://api.stackexchange.com/2.3/users/%d?order=desc&sort=reputation&site=stackoverflow",
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
