package edu.java.scrapper.core.service.impl;

import edu.java.scrapper.api.bot.dto.request.LinkDto;
import edu.java.scrapper.api.bot.dto.request.LinkViewDto;
import edu.java.scrapper.api.bot.dto.request.UntrackLinkDto;
import edu.java.scrapper.core.dao.jpa.JpaChatDao;
import edu.java.scrapper.core.dao.jpa.JpaChatLinksDao;
import edu.java.scrapper.core.dao.jpa.JpaLinkDao;
import edu.java.scrapper.core.dao.jpa.JpaMapper;
import edu.java.scrapper.core.dao.jpa.entity.JpaChat;
import edu.java.scrapper.core.dao.jpa.entity.JpaChatLinks;
import edu.java.scrapper.core.dao.jpa.entity.JpaLink;
import edu.java.scrapper.core.service.ChatService;
import edu.java.scrapper.core.service.LinkService;
import edu.java.scrapper.entity.Chat;
import edu.java.scrapper.entity.Link;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SuppressWarnings("MultipleStringLiterals") // TODO: add prettify exceptions
public class JpaLinkService implements LinkService {

    private final JpaChatDao chatDao;

    private final JpaLinkDao linkDao;

    private final JpaChatLinksDao chatLinksDao;

    private final ChatService chatService;

    @Override
    public void track(LinkDto linkDto) {
        JpaLink link = linkDao.findByLvalue(linkDto.getLvalue())
            .orElseGet(() -> JpaMapper.map(create(linkDto.getLvalue())));

        JpaChat chat = chatDao.findByTgChatId(linkDto.getChatId())
            .orElseGet(() -> JpaMapper.map(chatService.create(linkDto.getChatId())));

        JpaChatLinks jpaChatLinks = new JpaChatLinks();
        jpaChatLinks.setChatId(chat.getId());
        jpaChatLinks.setLinkId(link.getId());
        jpaChatLinks.setShortName(linkDto.getShortName());
        chatLinksDao.save(jpaChatLinks);
    }

    @Override
    public void untrack(UntrackLinkDto linkDto) {
        JpaChat chat = chatDao.findByTgChatId(linkDto.getChatId())
            .orElseThrow(() -> new RuntimeException("can't find such chat!"));

        chatLinksDao.findByChatIdAndShortName(chat.getId(), linkDto.getShortName())
            .ifPresent(chatLinks -> chatLinksDao.deleteById(chatLinks.getId()));
    }

    @Override
    public List<Link> getAllLinksSortedByUpdateDate(int pageSize) {
        return linkDao.getLinksSortedByLastUpdate(pageSize)
            .stream()
            .map(JpaMapper::map)
            .toList();
    }

    @Override
    public List<LinkViewDto> getAllForChat(long tgChatId) {
        JpaChat chat = chatDao.findByTgChatId(tgChatId)
            .orElseThrow(() -> new RuntimeException("can't find such chat!"));

        return chat.getLinks()
            .stream()
            .map(link -> {
                JpaChatLinks chatLinks = chatLinksDao.findByChatIdAndLinkId(chat.getId(), link.getId())
                    .orElseThrow(() -> new RuntimeException("can't find such subscription!"));

                return LinkViewDto.builder()
                    .shortName(chatLinks.getShortName())
                    .lvalue(link.getLvalue())
                    .build();
            })
            .toList();
    }

    @Override
    public List<Chat> findAllChatsConnectedWithLink(String value) {
        var link = linkDao.findByLvalue(value)
            .orElseThrow(() -> new RuntimeException("can't find such chat!"));

        return link.getChats()
            .stream()
            .map(JpaMapper::map)
            .toList();
    }

    @Override
    public void update(Link link, int newHashsum) {
        JpaLink jpaLink = linkDao.findById(link.getId())
            .orElseThrow(() -> new RuntimeException("can't find such link!"));

        jpaLink.setHashsum(newHashsum);
        linkDao.save(jpaLink);
    }

    @Override
    public String getShortName(long tgChatId, String value) {
        JpaLink link = linkDao.findByLvalue(value)
            .orElseThrow(() -> new RuntimeException("can't find such link!"));

        JpaChat chat = chatDao.findByTgChatId(tgChatId)
            .orElseThrow(() -> new RuntimeException("can't find such chat!"));

        JpaChatLinks chatLinks = chatLinksDao.findByChatIdAndLinkId(chat.getId(), link.getId())
            .orElseThrow(() -> new RuntimeException("can't find such subscription!"));

        return chatLinks.getShortName();
    }

    @Override
    public Link create(String value) {
        JpaLink jpaLink = new JpaLink();
        jpaLink.setLvalue(value);
        jpaLink.setLastUpdateAt(new Date());
        jpaLink.setHashsum(0);
        linkDao.save(jpaLink);

        return JpaMapper.map(linkDao.findByLvalue(value).get());
    }

    @Override
    public void deleteUntracked() {
        linkDao.findAll()
            .stream()
            .filter(link -> link.getChats().isEmpty())
            .forEach(linkDao::delete);
    }
}
