package edu.java.scrapper.api.resources.client;

import edu.java.scrapper.api.resources.dto.github.GithubCommitResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class GithubClient {

    private final WebClient webClient;

    private final String baseUrl;

    public GithubClient() {
        this.webClient = WebClient.builder().build();
        this.baseUrl = "https://api.github.com";
    }

    public Mono<ResponseEntity<GithubCommitResponse[]>> getUpdates(String owner, String repo) {
        return webClient.method(HttpMethod.GET)
            .uri(String.format(
                baseUrl + "/repos/%s/%s/commits",
                owner,
                repo
            ))
            .retrieve()
            .toEntity(GithubCommitResponse[].class);
    }

}