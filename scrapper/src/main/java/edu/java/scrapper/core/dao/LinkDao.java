package edu.java.scrapper.core.dao;

import edu.java.scrapper.api.bot.dto.request.LinkViewDto;
import edu.java.scrapper.entity.Link;
import java.util.List;
import java.util.Optional;

public interface LinkDao {

    List<Link> findAll();

    List<Link> findAllLinksSortedByUpdateDate(int pageSize);

    List<LinkViewDto> findAllLinksForChat(long tgChatId);

    Optional<Link> findByValue(String value);

    Optional<Link> findByShortName(String shortName, long tgChatId);

    void add(Link link);

    void update(long id, int newHashsum);

    void delete(long id);

}
