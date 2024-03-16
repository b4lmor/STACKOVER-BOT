package edu.java.scrapper.api.bot.client;

import edu.java.scrapper.api.bot.dto.response.UpdateDto;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
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
        this.baseUrl = "http://localhost:8090/" + BOT;
    }

    public void sendUpdates(List<UpdateDto> updateDto) {
        webClient.method(HttpMethod.PUT)
            .uri(baseUrl + UPDATES)
            .bodyValue(updateDto)
            .exchange()
            .block();
    }

}
