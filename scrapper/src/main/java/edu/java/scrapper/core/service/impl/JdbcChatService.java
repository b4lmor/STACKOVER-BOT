package edu.java.scrapper.core.service.impl;

import edu.java.scrapper.api.bot.dto.response.IsActiveChatDto;
import edu.java.scrapper.core.dao.jdbc.JdbcChatDao;
import edu.java.scrapper.core.service.ChatService;
import edu.java.scrapper.entity.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JdbcChatService implements ChatService {

    private final JdbcChatDao jdbcChatDao;

    @Override
    public IsActiveChatDto checkIfActivated(long tgChatId) {
        if (jdbcChatDao.findByTgChatId(tgChatId).isEmpty()) {
            create(tgChatId);
        }

        return IsActiveChatDto.builder()
            .isOpen(jdbcChatDao.findByTgChatId(tgChatId).get().getIsActive())
            .build();
    }

    @Override
    public void activate(long tgChatId) {
        if (jdbcChatDao.findByTgChatId(tgChatId).isEmpty()) {
            create(tgChatId);
        }

        jdbcChatDao.changeStatus(tgChatId, true);
    }

    @Override
    public void delete(long tgChatId) {
        jdbcChatDao.deleteByTgChatId(tgChatId);
    }

    @Override
    public Chat create(long tgChatId) {
        Chat chat = new Chat();
        chat.setTgChatId(tgChatId);
        jdbcChatDao.add(chat);
        return jdbcChatDao.findByTgChatId(tgChatId).get();
    }

}
