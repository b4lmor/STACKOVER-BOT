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

    public List<Link> getAll(Long userId) {
        return links.getOrDefault(userId, List.of());
    }

    public void save(Long userId, Link link) {
        if (links.containsKey(userId)) {
            links.get(userId).add(link);
        } else {
            List<Link> newLinks = new ArrayList<>();
            newLinks.add(link);
            links.put(userId, newLinks);
        }
    }

    public void delete(Long userId, String linkName) {
        if (links.containsKey(userId)) {
            var userLinks = links.get(userId);
            links.put(
                userId,
                userLinks.stream()
                    .filter(link -> !link.getName().equals(linkName))
                    .toList()
            );
        }
    }

}
