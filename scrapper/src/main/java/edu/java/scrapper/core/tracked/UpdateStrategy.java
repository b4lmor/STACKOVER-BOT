package edu.java.scrapper.core.tracked;

import edu.java.scrapper.api.services.client.GithubClient;
import edu.java.scrapper.api.services.client.StackOverflowClient;
import edu.java.scrapper.core.tracked.link.impl.GhParsedLink;
import edu.java.scrapper.core.tracked.link.impl.SofParsedLink;
import edu.java.scrapper.entity.TrackingResource;
import java.util.Objects;

public class UpdateStrategy {

    private final GithubClient githubClient = new GithubClient();

    private final StackOverflowClient stackOverflowClient = new StackOverflowClient();

    public boolean checkIfUpdated(int prevHashsum, String link) {
        return prevHashsum != countHashsum(link);
    }

    public int countHashsum(String link) {
        TrackingResource trackingResource = TrackingResource.parseResource(link);
        int hashsum = 0;
        switch (trackingResource) {
            case GITHUB -> {
                var parsedLink = GhParsedLink.ofLink(link);
                var githubClientUpdates = githubClient.getUpdates(parsedLink.getOwner(), parsedLink.getRepo())
                    .toStream()
                    .toList();
                hashsum = githubClientUpdates.stream()
                    .map(Objects::hashCode)
                    .reduce(Integer::sum)
                    .get();
            }
            case STACKOVERFLOW -> {
                var parsedLink = SofParsedLink.ofLink(link);
                var stackOverFlowUpdates = stackOverflowClient.getUpdates(parsedLink.getQuestionId())
                    .block()
                    .getItems();
                hashsum = stackOverFlowUpdates.stream()
                    .map(Objects::hashCode)
                    .reduce(Integer::sum)
                    .get();
            }
            default -> throw new RuntimeException("Internal server error");
        }
        return hashsum;
    }

}
