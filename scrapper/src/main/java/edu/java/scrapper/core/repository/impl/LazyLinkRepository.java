package edu.java.scrapper.core.repository.impl;

import edu.java.scrapper.core.repository.LinkRepository;
import edu.java.scrapper.entity.Link;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class LazyLinkRepository implements LinkRepository {

    private final Map<Long, Link> db = new HashMap<>();

    @Override
    public void save(Link link) {
        db.put(link.getChatId(), link);
    }

    @Override
    public Optional<Link> getById(long id) {
        return db.containsKey(id) ? Optional.of(db.get(id)) : Optional.empty();
    }

}
