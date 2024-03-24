package edu.java.scrapper.core.service.impl;

import edu.java.scrapper.api.bot.dto.request.LinkDto;
import edu.java.scrapper.api.bot.dto.request.LinkViewDto;
import edu.java.scrapper.api.bot.dto.request.UntrackLinkDto;
import edu.java.scrapper.core.dao.JdbcChatDao;
import edu.java.scrapper.core.dao.JdbcChatLinksDao;
import edu.java.scrapper.core.dao.JdbcLinkDao;
import edu.java.scrapper.core.service.ChatService;
import edu.java.scrapper.core.service.LinkService;
import edu.java.scrapper.entity.Chat;
import edu.java.scrapper.entity.ChatLinks;
import edu.java.scrapper.entity.Link;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JdbcLinkService implements LinkService {

    private final JdbcLinkDao jdbcLinkDao;

    private final JdbcChatDao jdbcChatDao;

    private final JdbcChatLinksDao jdbcChatLinksDao;

    private final ChatService chatService;

    @Override
    public void track(LinkDto linkDto) {
        Link link = jdbcLinkDao.findByValue(linkDto.getValue())
            .orElseGet(() -> this.create(linkDto.getValue(), linkDto.getShortName()));
        Chat chat = jdbcChatDao.findByTgChatId(linkDto.getChatId())
            .orElseGet(() -> chatService.create(linkDto.getChatId()));

        if (jdbcChatLinksDao.findByChatIdAndLinkId(chat.getId(), link.getId()).isPresent()) {
            return;
        }

        ChatLinks chatLinks = new ChatLinks();
        chatLinks.setChatId(chat.getId());
        chatLinks.setLinkId(link.getId());
        chatLinks.setShortName(linkDto.getShortName());
        jdbcChatLinksDao.add(chatLinks);
    }

    @Override
    public void untrack(UntrackLinkDto untrackLinkDto) {
        var optionalLink = jdbcLinkDao.findByShortName(untrackLinkDto.getShortName(), untrackLinkDto.getChatId());
        var optionalChat = jdbcChatDao.findByTgChatId(untrackLinkDto.getChatId());

        if (optionalLink.isEmpty() || optionalChat.isEmpty()) {
            return;
        }

        jdbcChatLinksDao.findByChatIdAndLinkId(optionalChat.get().getId(), optionalLink.get().getId())
            .ifPresent(chatLinks -> jdbcChatLinksDao.delete(chatLinks.getId()));
    }

    @Override
    public List<Link> getAllLinksSortedByUpdateDate(int pageSize) {
        return jdbcLinkDao.findAllLinksSortedByUpdateDate(pageSize);
    }

    @Override
    public List<LinkViewDto> getAllForChat(long tgChatId) {
        return jdbcLinkDao.findAllLinksForChat(tgChatId);
    }

    @Override
    public List<Chat> findAllChatsConnectedWithLink(String value) {
        return jdbcChatLinksDao.findAllChatsConnectedWithLink(value);
    }

    @Override
    public void update(Link link, int newHashsum) {
        jdbcLinkDao.update(link.getId(), newHashsum);
    }

    @Override
    public String getShortName(long tgChatId, String value) {
        return jdbcChatLinksDao.getShortName(tgChatId, value);
    }

    @Override
    public Link create(String value, String shortName) {
        Link link = new Link();
        link.setValue(value);
        link.setHashsum(0);
        jdbcLinkDao.add(link);
        return jdbcLinkDao.findByValue(value).get();
    }

    @Override
    public void deleteUntracked() {
        var trackedLinksId = jdbcChatLinksDao.findAll()
            .stream()
            .map(ChatLinks::getLinkId)
            .toList();

        jdbcLinkDao.findAll()
            .stream()
            .filter(link -> !trackedLinksId.contains(link.getId()))
            .forEach(link -> jdbcLinkDao.delete(link.getId()));
    }

}
