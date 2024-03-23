package edu.java.scrapper.core.scheduled;

import edu.java.scrapper.api.bot.client.BotClient;
import edu.java.scrapper.api.bot.dto.response.UpdateDto;
import edu.java.scrapper.core.service.LinkService;
import edu.java.scrapper.core.tracked.UpdateStrategy;
import edu.java.scrapper.entity.Link;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Log4j2
public class LinkUpdaterScheduler {

    private static final int LINKS_TO_UPDATE = 20;

    private final LinkService linkService;

    private final BotClient botClient;

    private final UpdateStrategy updateStrategy = new UpdateStrategy();

    @Scheduled(fixedDelayString = "#{@deleteInterval}")
    public void deleteUntracked() {
        log.trace("[SCHEDULED] :: Deleting untracked links ...");
        linkService.deleteUntracked();
        log.trace("[SCHEDULED] :: Deleting untracked links ... Done!");

    }

    @Scheduled(fixedDelayString = "#{@updateInterval}")
    public void update() {
        log.trace("[SCHEDULED] :: Updating links ...");

        var links = linkService.getAllLinksSortedByUpdateDate(LINKS_TO_UPDATE);
        List<Pair<Link, String>> updatedLinks = new ArrayList<>();

        links.forEach(link -> {
            var rawUpdate = updateStrategy.countHashsum(link.getLvalue());
            if (rawUpdate.newHashsum() != link.getHashsum()) {
                updatedLinks.add(Pair.of(link, rawUpdate.message()));
            }
            linkService.update(link, rawUpdate.newHashsum());
        });

        log.trace("[SCHEDULED] :: Sending updates ...");

        botClient.sendUpdates(updatedLinks.stream().map(pair -> this.getUpdates(pair.getLeft(), pair.getRight()))
            .flatMap(List::stream)
            .toList());

        log.trace("[SCHEDULED] :: Sending updates ... Done!");

        log.trace("[SCHEDULED] :: Updating links ... Done!");
    }

    private List<UpdateDto> getUpdates(Link link, String message) {
        var chats = linkService.findAllChatsConnectedWithLink(link.getLvalue());
        return chats.stream().map(chat -> {
                UpdateDto.UpdateBody body = new UpdateDto.UpdateBody(
                    link.getLvalue(),
                    linkService.getShortName(chat.getTgChatId(), link.getLvalue()),
                    message
                );
                return UpdateDto.builder().body(body).chatId(chat.getTgChatId()).build();
            })
            .toList();
    }

}
