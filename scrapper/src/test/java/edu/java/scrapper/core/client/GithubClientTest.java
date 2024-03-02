package edu.java.scrapper.core.client;

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

public class GithubClientTest {

    private WireMockServer wireMockServer;

    private GithubClient githubClient;

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();

        WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:" + wireMockServer.port())
            .build();

        githubClient = new GithubClient(webClient, "http://localhost:" + wireMockServer.port());
    }

    @AfterEach
    public void teardown() {
        wireMockServer.stop();
    }

    @Test
    public void testGetUpdates() {
        String owner = "test";
        String repo = "test-repo";
        String responseBody = "[{\"commit\":{\"message\":\"Test commit\",\"author\":{\"name\":\"Test author\"}}}]";

        stubFor(
            WireMock.get(urlEqualTo("/repos/" + owner + "/" + repo + "/commits"))
                .willReturn(aResponse()
                    .withStatus(HttpStatus.OK.value())
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .withBody(responseBody)
                )
        );

        var responseEntity = githubClient.getUpdates(owner, repo).block();

        assertThat(responseEntity.getStatusCode())
            .isEqualTo(HttpStatus.OK);

        assertThat(responseEntity.getBody().length)
            .isEqualTo(1);

        assertThat(responseEntity.getBody()[0].getCommitItem().getMessage())
            .isEqualTo("Test commit");

        assertThat(responseEntity.getBody()[0].getCommitItem().getAuthorItem().getName())
            .isEqualTo("Test author");

        wireMockServer.verify(
            getRequestedFor(urlEqualTo("/repos/" + owner + "/" + repo + "/commits"))
        );
    }

}
