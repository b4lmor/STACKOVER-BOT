package edu.java.scrapper.core.dao;

import edu.java.scrapper.entity.Chat;
import edu.java.scrapper.entity.ChatLinks;
import edu.java.scrapper.entity.Link;
import java.util.List;
import java.util.Optional;

public interface ChatLinksDao {

    List<ChatLinks> findAll();

    List<Chat> findAllChatsConnectedWithLink(String value);

    List<Link> findAllLinksConnectedWithChat(long tgChatId);

    Optional<ChatLinks> findByChatIdAndLinkId(long chatId, long linkId);

    String getShortName(long tgChatId, String value);

    void add(ChatLinks chatLinks);

    void delete(long id);

}
