package edu.java.scrapper.core.dao.jdbc;

import edu.java.scrapper.api.bot.dto.request.LinkViewDto;
import edu.java.scrapper.core.dao.LinkDao;
import edu.java.scrapper.core.dao.jdbc.mapper.LinkMapper;
import edu.java.scrapper.core.dao.jdbc.mapper.LinkViewDtoMapper;
import edu.java.scrapper.entity.Link;
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
public class JdbcLinkDao implements LinkDao {

    private final JdbcTemplate jdbcTemplate;

    private final LinkMapper linkMapper = new LinkMapper();

    private final LinkViewDtoMapper linkViewDtoMapper = new LinkViewDtoMapper();

    public Optional<Link> findByValue(String value) {
        String sql = "SELECT * FROM links WHERE lvalue=?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[] {value}, linkMapper));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Link> findByShortName(String shortName, long tgChatId) {
        String sql =
            "SELECT link_id AS id, lvalue, hashsum, last_update_at FROM links "
                + "JOIN chat_links cl on links.id = cl.link_id "
                + "JOIN chats c on c.id = cl.chat_id "
                + "WHERE cl.short_name=? AND c.tg_chat_id=?";
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(sql, new Object[] {shortName, tgChatId}, linkMapper)
            );
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Link> findAll() {
        String sql = "SELECT * FROM links ";
        return jdbcTemplate.query(sql, linkMapper);
    }

    public List<Link> findAllLinksSortedByUpdateDate(int pageSize) {
        String sql = "SELECT * FROM links ORDER BY last_update_at LIMIT ?";
        return jdbcTemplate.query(sql, new Object[] {pageSize}, linkMapper);
    }

    public List<LinkViewDto> findAllLinksForChat(long tgChatId) {
        String sql =
            "SELECT lvalue, short_name FROM links "
                + "JOIN chat_links cl on links.id = cl.link_id "
                + "JOIN chats c on c.id = cl.chat_id "
                + "WHERE tg_chat_id=?";
        return jdbcTemplate.query(sql, new Object[] {tgChatId}, linkViewDtoMapper);
    }

    @Transactional
    public void add(Link link) {
        String sql = "INSERT INTO links (lvalue, hashsum) VALUES (?, ?)";
        jdbcTemplate.update(sql, link.getLvalue(), 0);
    }

    @Transactional
    public void delete(long id) {
        String sql = "DELETE FROM links where id=?";
        jdbcTemplate.update(sql, id);
    }

    @Transactional
    public void update(long id, int newHashsum) {
        String sql = "UPDATE links SET last_update_at=CURRENT_TIMESTAMP, hashsum=? WHERE id=?";
        jdbcTemplate.update(sql, newHashsum, id);
    }

}
