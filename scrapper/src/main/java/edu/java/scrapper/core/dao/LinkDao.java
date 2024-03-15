package edu.java.scrapper.core.dao;

import edu.java.scrapper.core.jdbc.mapper.LinkMapper;
import edu.java.scrapper.entity.Link;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LinkDao {

    private final JdbcTemplate jdbcTemplate;
    private final LinkMapper linkMapper = new LinkMapper();

    public List<Link> findAll() {
        String sql =
            "SELECT chat_id, tg_chat_id, created_at, created_by, link_id, value FROM links " +
            "JOIN chat_links cl on links.id = cl.link_id " +
            "JOIN chats c on c.id = cl.chat_id";
        return jdbcTemplate.query(sql, linkMapper);
    }

    public void add(Link link) {
        String sql = "INSERT INTO links (value) VALUES (?)";
        jdbcTemplate.update(sql, link.getValue());
    }

    public void delete(long id) {
        String sql = "DELETE FROM links where id=?";
        jdbcTemplate.update(sql, id);
    }

}
