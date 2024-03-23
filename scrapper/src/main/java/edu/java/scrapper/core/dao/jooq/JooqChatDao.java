package edu.java.scrapper.core.dao.jooq;

import edu.java.scrapper.core.dao.ChatDao;
import edu.java.scrapper.entity.Chat;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import static edu.java.scrapper.core.dao.jooq.generated.Tables.CHATS;

@Repository
@RequiredArgsConstructor
public class JooqChatDao implements ChatDao {

    private final DSLContext dsl;

    @Override
    public List<Chat> findAll() {
        return dsl.select(CHATS.fields())
            .from(CHATS)
            .fetchInto(Chat.class);
    }

    @Override
    public Optional<Chat> findByTgChatId(long tgChatID) {
        return dsl.select()
            .from(CHATS)
            .where(CHATS.TG_CHAT_ID.eq(tgChatID))
            .fetchOptionalInto(Chat.class);
    }

    @Override
    public void add(Chat chat) {
        dsl.insertInto(CHATS, CHATS.TG_CHAT_ID)
            .values(chat.getTgChatId())
            .execute();
    }

    @Override
    public void changeStatus(long tgChatId, boolean status) {
        dsl.update(CHATS)
            .set(CHATS.IS_ACTIVE, status)
            .where(CHATS.TG_CHAT_ID.eq(tgChatId))
            .execute();
    }

    @Override
    public void delete(long id) {
        dsl.deleteFrom(CHATS)
            .where(CHATS.ID.eq(id))
            .execute();
    }

    @Override
    public void deleteByTgChatId(long tgChatId) {
        dsl.deleteFrom(CHATS)
            .where(CHATS.TG_CHAT_ID.eq(tgChatId))
            .execute();
    }

}
