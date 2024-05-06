package edu.java.scrapper.core.service.impl;

import edu.java.scrapper.api.bot.dto.response.IsActiveChatDto;
import edu.java.scrapper.core.dao.jpa.JpaChatDao;
import edu.java.scrapper.core.dao.jpa.JpaMapper;
import edu.java.scrapper.core.dao.jpa.entity.JpaChat;
import edu.java.scrapper.core.service.ChatService;
import edu.java.scrapper.entity.Chat;
import java.util.Date;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaChatService implements ChatService {

    private final JpaChatDao chatDao;

    @Override
    public IsActiveChatDto checkIfActivated(long tgChatId) {
        var chat = chatDao.findByTgChatId(tgChatId);
        if (chatDao.findByTgChatId(tgChatId).isEmpty()) {
            create(tgChatId);
        }
        return IsActiveChatDto.builder()
            .isOpen(chat.isPresent() && chat.get().isActive())
            .build();
    }

    @Override
    public void activate(long tgChatId) {
        if (chatDao.findByTgChatId(tgChatId).isEmpty()) {
            create(tgChatId);
        }
        var chat = chatDao.findByTgChatId(tgChatId).get();
        chat.setActive(true);
        chatDao.save(chat);
    }

    @Override
    public void delete(long tgChatId) {
        chatDao.deleteByTgChatId(tgChatId);
    }

    @Override
    public Chat create(long tgChatId) {
        JpaChat chat = new JpaChat();
        chat.setTgChatId(tgChatId);
        chat.setCreatedAt(new Date());
        chat.setActive(false);
        chatDao.save(chat);
        return JpaMapper.map(chatDao.findByTgChatId(tgChatId).get());
    }

}
