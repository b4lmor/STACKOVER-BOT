package edu.java.scrapper.core.sources;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class StackOverflowServiceTest {

    private WireMockServer wireMockServer;

    private StackOverflowService stackOverflowService;

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();

        WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:" + wireMockServer.port())
            .build();

        stackOverflowService = new StackOverflowService(webClient, "http://localhost:" + wireMockServer.port());
    }

    @AfterEach
    public void teardown() {
        wireMockServer.stop();
    }

    @Test
    public void testGetUpdates() {
        long questionId = 999999;
        String responseBody = "{\"items\":[{\"owner\":{\"user_id\":1234},\"body\":\"test body\"}]}";

        stubFor(
            WireMock.get(urlEqualTo("/questions/" + questionId + "/answers?site=stackoverflow&filter=withbody"))
                .willReturn(aResponse()
                    .withStatus(HttpStatus.OK.value())
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .withBody(responseBody)
                )
        );

        var responseEntity = stackOverflowService.getUpdates(questionId).block();

        assertThat(responseEntity.getStatusCode())
            .isEqualTo(HttpStatus.OK);

        assertThat(responseEntity.getBody().size())
            .isEqualTo(1);

        assertThat(responseEntity.getBody().getFirst().getUserId())
            .isEqualTo(1234);

        assertThat(responseEntity.getBody().getFirst().getBody())
            .isEqualTo("test body");

        wireMockServer.verify(
            getRequestedFor(urlEqualTo(
                "/questions/" + questionId + "/answers?site=stackoverflow&filter=withbody")
            )
        );
    }

    @Test
    public void testGetUserName() {
        long userId = 999999;
        String responseBody = "{\"items\":[{\"display_name\":\"test name\"}]}";

        stubFor(
            WireMock.get(urlEqualTo("/users/" + userId + "?site=stackoverflow"))
                .willReturn(aResponse()
                    .withStatus(HttpStatus.OK.value())
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .withBody(responseBody)
                )
        );

        var responseEntity = stackOverflowService.getUserName(userId).block();

        assertThat(responseEntity.getStatusCode())
            .isEqualTo(HttpStatus.OK);

        assertThat(responseEntity.getBody())
            .isEqualTo("test name");

        wireMockServer.verify(
            getRequestedFor(urlEqualTo(
                "/users/" + userId + "?site=stackoverflow")
            )
        );
    }

}
