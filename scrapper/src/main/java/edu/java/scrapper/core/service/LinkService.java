package edu.java.scrapper.core.service;

import edu.java.scrapper.api.bot.dto.request.LinkDto;
import edu.java.scrapper.api.bot.dto.request.LinkViewDto;
import edu.java.scrapper.api.bot.dto.request.UntrackLinkDto;
import edu.java.scrapper.entity.Chat;
import edu.java.scrapper.entity.Link;
import java.util.List;

public interface LinkService {

    void track(LinkDto linkDto);

    void untrack(UntrackLinkDto untrackLinkDto);

    List<Link> getAllLinksSortedByUpdateDate(int pageSize);

    List<LinkViewDto> getAllForChat(long tgChatId);

    List<Chat> findAllChatsConnectedWithLink(String value);

    void update(Link link, int newHashsum);

    String getShortName(long tgChatId, String value);

    Link create(String value);

    void deleteUntracked();

}
