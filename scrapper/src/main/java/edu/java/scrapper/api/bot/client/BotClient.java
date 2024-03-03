package edu.java.scrapper.api.bot.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.scrapper.api.bot.dto.response.UpdateDto;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import static edu.java.scrapper.api.bot.ApiPath.BOT;
import static edu.java.scrapper.api.bot.ApiPath.UPDATES;

@Component
@AllArgsConstructor
public class BotClient {

    private final WebClient webClient;

    private final String baseUrl;

    public BotClient() {
        this.webClient = WebClient.create();
        this.baseUrl = "localhost:8080/" + BOT;
    }

    @SneakyThrows
    public void sendUpdates(UpdateDto updateDto) {
        webClient.method(HttpMethod.PUT)
            .uri(baseUrl + UPDATES)
            .body(
                BodyInserters.fromValue(
                    new ObjectMapper().writeValueAsString(
                        updateDto
                    )
                )
            )
            .retrieve()
            .toBodilessEntity()
            .block();
    }

}
