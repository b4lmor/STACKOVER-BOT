package edu.java.scrapper.core.repository;

import edu.java.scrapper.entity.Link;
import java.util.Optional;

public interface LinkRepository {

    void save(Link link);

    Optional<Link> getById(long id);

}
