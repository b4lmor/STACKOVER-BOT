package edu.java.scrapper.core.dao.jdbc;

import edu.java.scrapper.core.dao.ChatDao;
import edu.java.scrapper.core.dao.jdbc.mapper.ChatMapper;
import edu.java.scrapper.entity.Chat;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@SuppressWarnings("MultipleStringLiterals")
@RequiredArgsConstructor
public class JdbcChatDao implements ChatDao {

    private final JdbcTemplate jdbcTemplate;

    private final ChatMapper chatMapper = new ChatMapper();

    public Optional<Chat> findByTgChatId(long tgChatID) {
        String sql = "SELECT * FROM chats WHERE tg_chat_id=?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{tgChatID}, chatMapper));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Chat> findAll() {
        String sql = "SELECT * FROM chats ";
        return jdbcTemplate.query(sql, chatMapper);
    }

    @Transactional
    public void add(Chat chat) {
        String sql = "INSERT INTO chats (tg_chat_id) VALUES (?)";
        jdbcTemplate.update(sql, chat.getTgChatId());
    }

    @Transactional
    public void delete(long id) {
        String sql = "DELETE FROM chats where id=?";
        jdbcTemplate.update(sql, id);
    }

    @Transactional
    public void deleteByTgChatId(long tgChatId) {
        String sql = "DELETE FROM chats where tg_chat_id=?";
        jdbcTemplate.update(sql, tgChatId);
    }

    @Transactional
    public void changeStatus(long tgChatId, boolean status) {
        String sql = "UPDATE chats SET is_active=? WHERE tg_chat_id=?";
        jdbcTemplate.update(sql, status, tgChatId);
    }

}
