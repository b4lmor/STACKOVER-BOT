package edu.java.scrapper.core.dao;

import edu.java.scrapper.entity.Chat;
import java.util.List;
import java.util.Optional;

public interface ChatDao {

    List<Chat> findAll();

    Optional<Chat> findByTgChatId(long tgChatID);

    void add(Chat chat);

    void changeStatus(long tgChatId, boolean status);

    void delete(long id);

    void deleteByTgChatId(long tgChatId);

}
