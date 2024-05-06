package edu.java.scrapper.core.dao.jpa;

import edu.java.scrapper.core.dao.jpa.entity.JpaChat;
import edu.java.scrapper.core.dao.jpa.entity.JpaLink;
import edu.java.scrapper.entity.Chat;
import edu.java.scrapper.entity.Link;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JpaMapper {

    public static Link map(JpaLink jpaLink) {
        Link link = new Link();
        link.setLvalue(jpaLink.getLvalue());
        link.setId(jpaLink.getId());
        link.setHashsum(jpaLink.getHashsum());
        link.setLastUpdateAt(jpaLink.getLastUpdateAt());
        return link;
    }

    public static JpaLink map(Link link) {
        JpaLink jpaLink = new JpaLink();
        jpaLink.setLvalue(link.getLvalue());
        jpaLink.setHashsum(link.getHashsum());
        jpaLink.setId(link.getId());
        jpaLink.setLastUpdateAt(link.getLastUpdateAt());
        return jpaLink;
    }

    public static Chat map(JpaChat jpaChat) {
        Chat chat = new Chat();
        chat.setId(jpaChat.getId());
        chat.setTgChatId(jpaChat.getTgChatId());
        chat.setCreatedAt(jpaChat.getCreatedAt());
        chat.setIsActive(jpaChat.isActive());
        return chat;
    }

    public static JpaChat map(Chat chat) {
        JpaChat jpaChat = new JpaChat();
        jpaChat.setId(chat.getId());
        jpaChat.setTgChatId(chat.getTgChatId());
        jpaChat.setCreatedAt(chat.getCreatedAt());
        jpaChat.setActive(chat.getIsActive());
        return jpaChat;
    }

}
