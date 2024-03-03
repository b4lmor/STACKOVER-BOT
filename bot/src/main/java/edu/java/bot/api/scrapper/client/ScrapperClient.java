package edu.java.bot.api.scrapper.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.bot.api.scrapper.dto.request.LinkDto;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import static edu.java.bot.api.scrapper.ApiPath.CHAT;
import static edu.java.bot.api.scrapper.ApiPath.LINK;
import static edu.java.bot.api.scrapper.ApiPath.SCRAPPER;

@Component
@AllArgsConstructor
public class ScrapperClient {

    private final WebClient webClient;

    private final String baseUrl;

    public ScrapperClient() {
        this.webClient = WebClient.create();
        this.baseUrl = "localhost:8080/" + SCRAPPER;
    }

    @SneakyThrows
    public void sendLink(LinkDto linkDto) {
        webClient.method(HttpMethod.POST)
            .uri(baseUrl + LINK)
            .body(
                BodyInserters.fromValue(
                    new ObjectMapper().writeValueAsString(
                        linkDto
                    )
                )
            )
            .retrieve()
            .toBodilessEntity()
            .block();
    }

    public void openChat(long chatId) {
        webClient.method(HttpMethod.PUT)
            .uri(baseUrl + CHAT + chatId)
            .retrieve()
            .toBodilessEntity()
            .block();
    }

    public boolean checkChat(long chatId) {
        var response = webClient.method(HttpMethod.GET)
                .uri(baseUrl + CHAT + chatId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        return response != null && response;
    }
}
