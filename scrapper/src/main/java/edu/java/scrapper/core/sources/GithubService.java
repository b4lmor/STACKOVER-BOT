package edu.java.scrapper.core.sources;

import edu.java.scrapper.api.dto.github.GithubCommitResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class GithubService {

    private final WebClient webClient;

    public GithubService() {
        this.webClient = WebClient.builder().build();
    }

    public Mono<ResponseEntity<GithubCommitResponse[]>> getUpdates(String owner, String repo) {
        return webClient.method(HttpMethod.GET)
            .uri(String.format(
                "https://api.github.com/repos/%s/%s/commits",
                owner,
                repo
            ))
            .retrieve()
            .toEntity(GithubCommitResponse[].class);
    }

}
