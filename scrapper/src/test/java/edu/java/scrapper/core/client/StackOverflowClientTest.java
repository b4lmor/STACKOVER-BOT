package edu.java.scrapper.core.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.scrapper.api.services.client.StackOverflowClient;
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

public class StackOverflowClientTest {

    private WireMockServer wireMockServer;

    private StackOverflowClient stackOverflowClient;

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();

        WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:" + wireMockServer.port())
            .build();

        stackOverflowClient = new StackOverflowClient(webClient, "http://localhost:" + wireMockServer.port());
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

        var responseEntity = stackOverflowClient.getUpdates(questionId).block().getItems();

        assertThat(responseEntity.size())
            .isEqualTo(1);

        assertThat(responseEntity.getFirst().getOwner().getUserId())
            .isEqualTo(1234);

        assertThat(responseEntity.getFirst().getBody())
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

        var responseEntity = stackOverflowClient.getUserName(userId);

        assertThat(responseEntity)
            .isEqualTo("test name");

        wireMockServer.verify(
            getRequestedFor(urlEqualTo(
                "/users/" + userId + "?site=stackoverflow")
            )
        );
    }

}
