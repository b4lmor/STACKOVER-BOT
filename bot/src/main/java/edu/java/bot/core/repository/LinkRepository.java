package edu.java.bot.core.repository;

import edu.java.bot.entity.Link;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class LinkRepository {

    private final HashMap<Long, List<Link>> links;

    public LinkRepository() {
        links = new HashMap<>();
    }

    public List<Link> getAll(Long userId) {
        return links.get(userId);
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
}
