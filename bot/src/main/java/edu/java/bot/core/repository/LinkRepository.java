package edu.java.bot.core.repository;

import edu.java.bot.entity.Link;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class LinkRepository {

    private final HashMap<Long, List<Link>> links;

    public LinkRepository() {
        links = new HashMap<>();
    }

    public List<Link> getAll(Long chatId) {
        return links.getOrDefault(chatId, List.of());
    }

    public void save(Long chatId, Link link) {
        if (links.containsKey(chatId)) {
            links.get(chatId).add(link);
        } else {
            List<Link> newLinks = new ArrayList<>();
            newLinks.add(link);
            links.put(chatId, newLinks);
        }
    }

    public void delete(Long chatId, String linkName) {
        if (links.containsKey(chatId)) {
            var userLinks = links.get(chatId);
            links.put(
                chatId,
                userLinks.stream()
                    .filter(link -> !link.getName().equals(linkName))
                    .toList()
            );
        }
    }

}
