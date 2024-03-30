package edu.java.bot.api.scrapper.client;

import edu.java.bot.api.retrying.RetryFilter;
import edu.java.bot.api.scrapper.dto.request.LinkDto;
import edu.java.bot.api.scrapper.dto.request.UntrackLinkDto;
import edu.java.bot.api.scrapper.dto.response.IsActiveChatDto;
import edu.java.bot.api.scrapper.dto.response.LinkViewDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import static edu.java.bot.api.scrapper.ApiPath.CHAT;
import static edu.java.bot.api.scrapper.ApiPath.LINK;
import static edu.java.bot.api.scrapper.ApiPath.SCRAPPER;

@AllArgsConstructor
@Log4j2
public class ScrapperClient {

    private final WebClient webClient;

    private final String baseUrl;

    public ScrapperClient(RetryFilter retryFilter) {
        this.webClient = WebClient.builder().filter(retryFilter).build();
        this.baseUrl = "http://localhost:8080/" + SCRAPPER;
    }

    public void trackLink(LinkDto linkDto) {
        log.trace("[SCRAPPER CLIENT] :: Sending linkDto ...");
        webClient.method(HttpMethod.POST)
            .uri(baseUrl + LINK)
            .bodyValue(linkDto)
            .exchange()
            .block();
        log.trace("[SCRAPPER CLIENT] :: Sending linkDto ... Done!");
    }

    public void untrackLink(UntrackLinkDto untrackLinkDto) {
        log.trace("[SCRAPPER CLIENT] :: Sending untrackLinkDto ...");
        webClient.method(HttpMethod.DELETE)
            .uri(baseUrl + LINK)
            .bodyValue(untrackLinkDto)
            .exchange()
            .block();
        log.trace("[SCRAPPER CLIENT] :: Sending untrackLinkDto ... Done!");
    }

    public List<LinkViewDto> getLinksForChat(long tgChatId) {
        return webClient.method(HttpMethod.GET)
            .uri(baseUrl + LINK + tgChatId + "/")
            .retrieve()
            .bodyToFlux(LinkViewDto.class)
            .toStream()
            .toList();
    }

    public void openChat(long chatId) {
        webClient.method(HttpMethod.PUT)
            .uri(baseUrl + CHAT + chatId + "/")
            .exchange()
            .block();
    }

    public boolean checkChat(long chatId) {
        var response = webClient.method(HttpMethod.GET)
                .uri(baseUrl + CHAT + chatId + "/")
                .retrieve()
                .bodyToMono(IsActiveChatDto.class)
                .block();

        return response != null && response.isOpen();
    }
}
