package edu.java.scrapper.core.repository.impl;

import edu.java.scrapper.core.repository.ChatRepository;
import edu.java.scrapper.entity.Chat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LazyChatRepository implements ChatRepository {

    private final Map<Long, Chat> db = new HashMap<>();

    @Override
    public void save(Chat chat) {
        db.put(chat.getChatTgId(), chat);
    }

    @Override
    public Optional<Chat> getById(long id) {
        return db.containsKey(id) ? Optional.of(db.get(id)) : Optional.empty();
    }
}
