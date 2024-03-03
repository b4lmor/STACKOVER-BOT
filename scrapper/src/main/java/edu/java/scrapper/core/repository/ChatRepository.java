package edu.java.scrapper.core.repository;

import edu.java.scrapper.entity.Chat;
import java.util.Optional;

public interface ChatRepository {

    void save(Chat chat);

    Optional<Chat> getById(long id);

}
