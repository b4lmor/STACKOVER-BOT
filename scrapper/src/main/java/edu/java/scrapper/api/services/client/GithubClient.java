package edu.java.scrapper.api.services.client;

import edu.java.scrapper.api.services.dto.github.GithubCommitResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
@AllArgsConstructor
public class GithubClient {

    private final WebClient webClient;

    private final String baseUrl;

    public GithubClient() {
        this.webClient = WebClient.builder().build();
        this.baseUrl = "https://api.github.com";
    }

    public Flux<GithubCommitResponse> getUpdates(String owner, String repo) {
        return webClient.method(HttpMethod.GET)
            .uri(String.format(baseUrl + "/repos/%s/%s/commits", owner, repo))
            .retrieve()
            .bodyToFlux(GithubCommitResponse.class);
    }

}
