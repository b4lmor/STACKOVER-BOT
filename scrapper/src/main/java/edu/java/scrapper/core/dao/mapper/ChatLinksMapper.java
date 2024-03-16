package edu.java.scrapper.core.dao.mapper;

import edu.java.scrapper.entity.ChatLinks;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ChatLinksMapper implements RowMapper<ChatLinks> {

    @Override
    public ChatLinks mapRow(ResultSet rs, int rowNum) throws SQLException {
        var chatLinks = new ChatLinks();

        chatLinks.setId(rs.getLong("id"));
        chatLinks.setChatId(rs.getLong("chat_id"));
        chatLinks.setLinkId(rs.getLong("link_id"));
        chatLinks.setShortName(rs.getString("short_name"));

        return chatLinks;
    }
}
