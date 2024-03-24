package edu.java.scrapper.core.service;

import edu.java.scrapper.api.bot.dto.response.IsActiveChatDto;
import edu.java.scrapper.entity.Chat;

public interface ChatService {

    IsActiveChatDto checkIfActivated(long tgChatId);

    void activate(long tgChatId);

    void delete(long tgChatId);

    Chat create(long tgChatId);
}
