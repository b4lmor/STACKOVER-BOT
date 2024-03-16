package edu.java.scrapper.core.dao.mapper;

import edu.java.scrapper.entity.Chat;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ChatMapper implements RowMapper<Chat> {

    @Override
    public Chat mapRow(ResultSet rs, int rowNum) throws SQLException {
        var chat = new Chat();

        chat.setId(rs.getLong("id"));
        chat.setTgChatId(rs.getLong("tg_chat_id"));
        chat.setCreatedAt(rs.getDate("created_at"));
        chat.setActive(rs.getBoolean("is_active"));

        return chat;
    }
}
