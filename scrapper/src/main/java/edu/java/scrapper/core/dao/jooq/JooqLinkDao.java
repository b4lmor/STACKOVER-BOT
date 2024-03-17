package edu.java.scrapper.core.dao.jooq;

import edu.java.scrapper.api.bot.dto.request.LinkViewDto;
import edu.java.scrapper.core.dao.LinkDao;
import edu.java.scrapper.entity.Link;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import static edu.java.scrapper.core.dao.jooq.generated.Tables.CHATS;
import static edu.java.scrapper.core.dao.jooq.generated.Tables.CHAT_LINKS;
import static edu.java.scrapper.core.dao.jooq.generated.Tables.LINKS;

@RequiredArgsConstructor
public class JooqLinkDao implements LinkDao {

    private final DSLContext dsl;

    @Override
    public List<Link> findAll() {
        return dsl.select(LINKS.fields())
            .from(LINKS)
            .fetchInto(Link.class);
    }

    @Override
    public List<Link> findAllLinksSortedByUpdateDate(int pageSize) {
        return dsl.select(LINKS.fields())
            .from(LINKS)
            .orderBy(LINKS.LAST_UPDATE_AT)
            .limit(pageSize)
            .fetchInto(Link.class);
    }

    @Override
    public List<LinkViewDto> findAllLinksForChat(long tgChatId) {
        return dsl.select(LINKS.LVALUE, CHAT_LINKS.SHORT_NAME)
            .from(LINKS)
            .join(CHAT_LINKS)
            .on(CHAT_LINKS.LINK_ID.eq(LINKS.ID))
            .join(CHATS)
            .on(CHAT_LINKS.CHAT_ID.eq(CHATS.ID))
            .where(CHATS.TG_CHAT_ID.eq(tgChatId))
            .fetchInto(LinkViewDto.class);
    }

    @Override
    public Optional<Link> findByValue(String value) {
        return dsl.select()
            .from(LINKS)
            .where(LINKS.LVALUE.eq(value))
            .fetchOptionalInto(Link.class);
    }

    @Override
    public Optional<Link> findByShortName(String shortName, long tgChatId) {
        return dsl.select(LINKS.LVALUE, CHAT_LINKS.SHORT_NAME)
            .from(LINKS)
            .join(CHAT_LINKS)
            .on(CHAT_LINKS.LINK_ID.eq(LINKS.ID))
            .join(CHATS)
            .on(CHAT_LINKS.CHAT_ID.eq(CHATS.ID))
            .where(
                CHAT_LINKS.SHORT_NAME.eq(shortName)
                    .and(CHATS.TG_CHAT_ID.eq(tgChatId))
            )
            .fetchOptionalInto(Link.class);
    }

    @Override
    public void add(Link link) {
        dsl.insertInto(LINKS, LINKS.LVALUE, LINKS.HASHSUM)
            .values(link.getLvalue(), 0)
            .execute();
    }

    @Override
    public void update(long id, int newHashsum) {
        dsl.update(LINKS)
            .set(LINKS.HASHSUM, newHashsum)
            .where(LINKS.ID.eq(id))
            .execute();
    }

    @Override
    public void delete(long id) {
        dsl.deleteFrom(LINKS)
            .where(LINKS.ID.eq(id))
            .execute();
    }

}
