package edu.java.scrapper.core.dao.jooq;

import edu.java.scrapper.core.dao.ChatLinksDao;
import edu.java.scrapper.entity.Chat;
import edu.java.scrapper.entity.ChatLinks;
import edu.java.scrapper.entity.Link;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import static edu.java.scrapper.core.dao.jooq.generated.Tables.CHATS;
import static edu.java.scrapper.core.dao.jooq.generated.Tables.CHAT_LINKS;
import static edu.java.scrapper.core.dao.jooq.generated.Tables.LINKS;

@Repository
@RequiredArgsConstructor
public class JooqChatLinksDao implements ChatLinksDao {

    private final DSLContext dsl;

    @Override
    public List<ChatLinks> findAll() {
        return dsl.select(CHAT_LINKS.fields())
            .from(CHAT_LINKS)
            .fetchInto(ChatLinks.class);
    }

    @Override
    public List<Chat> findAllChatsConnectedWithLink(String value) {
        return dsl.select(CHATS.fields())
            .from(LINKS)
            .join(CHAT_LINKS)
            .on(CHAT_LINKS.LINK_ID.eq(LINKS.ID))
            .join(CHATS)
            .on(CHAT_LINKS.CHAT_ID.eq(CHATS.ID))
            .where(LINKS.LVALUE.eq(value))
            .fetchInto(Chat.class);
    }

    @Override
    public List<Link> findAllLinksConnectedWithChat(long tgChatId) {
        return dsl.select(LINKS.fields())
            .from(LINKS)
            .join(CHAT_LINKS)
            .on(CHAT_LINKS.LINK_ID.eq(LINKS.ID))
            .join(CHATS)
            .on(CHAT_LINKS.CHAT_ID.eq(CHATS.ID))
            .where(CHATS.TG_CHAT_ID.eq(tgChatId))
            .fetchInto(Link.class);
    }

    @Override
    public Optional<ChatLinks> findByChatIdAndLinkId(long chatId, long linkId) {
        return dsl.select(CHAT_LINKS.fields())
            .from(LINKS)
            .join(CHAT_LINKS)
            .on(CHAT_LINKS.LINK_ID.eq(LINKS.ID))
            .join(CHATS)
            .on(CHAT_LINKS.CHAT_ID.eq(CHATS.ID))
            .where(
                CHAT_LINKS.CHAT_ID.eq(chatId)
                    .and(CHAT_LINKS.LINK_ID.eq(linkId))
            )
            .fetchOptionalInto(ChatLinks.class);
    }

    @Override
    public String getShortName(long tgChatId, String value) {
        return dsl.select(CHAT_LINKS.SHORT_NAME)
            .from(LINKS)
            .join(CHAT_LINKS)
            .on(CHAT_LINKS.LINK_ID.eq(LINKS.ID))
            .join(CHATS)
            .on(CHAT_LINKS.CHAT_ID.eq(CHATS.ID))
            .where(
                CHATS.TG_CHAT_ID.eq(tgChatId)
                .and(LINKS.LVALUE.eq(value))
            )
            .fetchOptionalInto(String.class)
            .orElse("");
    }

    @Override
    public void add(ChatLinks chatLinks) {
        dsl.insertInto(CHAT_LINKS, CHAT_LINKS.CHAT_ID, CHAT_LINKS.LINK_ID, CHAT_LINKS.SHORT_NAME)
            .values(chatLinks.getChatId(), chatLinks.getLinkId(), chatLinks.getShortName())
            .execute();
    }

    @Override
    public void delete(long id) {
        dsl.deleteFrom(CHAT_LINKS)
            .where(CHAT_LINKS.ID.eq(id))
            .execute();
    }
}
