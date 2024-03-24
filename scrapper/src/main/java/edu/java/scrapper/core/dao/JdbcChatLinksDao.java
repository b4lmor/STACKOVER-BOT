package edu.java.scrapper.core.dao;

import edu.java.scrapper.core.dao.mapper.ChatLinksMapper;
import edu.java.scrapper.core.dao.mapper.ChatMapper;
import edu.java.scrapper.core.dao.mapper.LinkMapper;
import edu.java.scrapper.entity.Chat;
import edu.java.scrapper.entity.ChatLinks;
import edu.java.scrapper.entity.Link;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@SuppressWarnings("MultipleStringLiterals")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JdbcChatLinksDao {

    private final JdbcTemplate jdbcTemplate;
    private final ChatLinksMapper chatLinksMapper = new ChatLinksMapper();
    private final LinkMapper linkMapper = new LinkMapper();
    private final ChatMapper chatMapper = new ChatMapper();

    public List<ChatLinks> findAll() {
        String sql = "SELECT * FROM chat_links";
        return jdbcTemplate.query(sql, chatLinksMapper);
    }

    public Optional<ChatLinks> findByChatIdAndLinkId(long chatId, long linkId) {
        String sql = "SELECT * FROM chat_links where chat_id=? AND link_id=?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                sql,
                new Object[] {chatId, linkId},
                chatLinksMapper
            ));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Chat> findAllChatsConnectedWithLink(String value) {
        String sql =
            "SELECT chat_id AS id, tg_chat_id, created_at, is_active FROM chat_links "
                + "JOIN chats c on c.id = chat_links.chat_id "
                + "JOIN public.links l on l.id = chat_links.link_id "
                + "WHERE l.value=?";
        return jdbcTemplate.query(sql, new Object[] {value}, chatMapper);
    }

    public List<Link> findAllLinksConnectedWithChat(long tgChatId) {
        String sql =
            "SELECT link_id AS id, value, hashsum FROM chat_links "
                + "JOIN chats c on c.id = chat_links.chat_id "
                + "JOIN links l on l.id = chat_links.link_id "
                + "WHERE c.tg_chat_id=?";
        return jdbcTemplate.query(sql, new Object[] {tgChatId}, linkMapper);
    }

    public String getShortName(long tgChatId, String value) {
        String sql =
            "SELECT short_name FROM chat_links "
                + "JOIN chats c on c.id = chat_links.chat_id "
                + "JOIN links l on l.id = chat_links.link_id "
                + "WHERE c.tg_chat_id=? AND value=?";
        return jdbcTemplate.queryForObject(sql, new Object[] {tgChatId, value}, String.class);
    }

    @Transactional
    public void add(ChatLinks chatLinks) {
        String sql = "INSERT INTO chat_links (chat_id, link_id, short_name) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, chatLinks.getChatId(), chatLinks.getLinkId(), chatLinks.getShortName());
    }

    @Transactional
    public void delete(long id) {
        String sql = "DELETE FROM chat_links where id=?";
        jdbcTemplate.update(sql, id);
    }

}
