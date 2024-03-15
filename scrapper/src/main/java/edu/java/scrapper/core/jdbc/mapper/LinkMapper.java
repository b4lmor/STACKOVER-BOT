package edu.java.scrapper.core.jdbc.mapper;

import edu.java.scrapper.entity.Chat;
import edu.java.scrapper.entity.Link;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LinkMapper implements RowMapper<Link> {

    @Override
    public Link mapRow(ResultSet rs, int rowNum) throws SQLException {
        var chat = new Chat();
        var link = new Link();

        chat.setId(rs.getLong("chat_id"));
        chat.setChatId(rs.getLong("tg_chat_id"));
        chat.setCreatedAt(rs.getDate("created_at"));
        chat.setCreatedBy(rs.getString("created_by"));

        link.setId(rs.getLong("link_id"));
        link.setChat(chat);
        link.setValue(rs.getString("value"));

        return link;
    }
}
