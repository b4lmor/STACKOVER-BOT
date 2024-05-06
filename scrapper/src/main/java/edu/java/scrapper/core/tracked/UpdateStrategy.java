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

    public RawUpdate countHashsum(String link) {
        TrackingResource trackingResource = TrackingResource.parseResource(link);
        int hashsum;
        String message;
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
                String rawCommitMessage = githubClientUpdates.getFirst()
                    .getCommitItem()
                    .getMessage();
                String commitMessage = rawCommitMessage
                    .substring(0, Math.min(RawUpdate.MAX_MESSAGE_LENGTH, rawCommitMessage.length()))
                    + RawUpdate.DOTS;
                String authorName = githubClientUpdates.getFirst().getCommitItem().getAuthorItem().getName();
                message = "New commit!\nAuthor: " + authorName + "\nMessage: " + commitMessage;
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

                String authorName = stackOverflowClient.getUserName(
                    stackOverFlowUpdates.getFirst().getOwner().getUserId()
                );
                String rawText = stackOverFlowUpdates.getFirst()
                    .getBody();
                String text = rawText.substring(
                    RawUpdate.SOF_SKIP_MESSAGE_LENGTH,
                    Math.min(RawUpdate.MAX_MESSAGE_LENGTH, rawText.length())
                ) + RawUpdate.DOTS;
                message = "New answer!\nAuthor: " + authorName + "\nText: " + text;
            }
            default -> throw new RuntimeException("Internal server error");
        }
        return new RawUpdate(hashsum, message);
    }

}
