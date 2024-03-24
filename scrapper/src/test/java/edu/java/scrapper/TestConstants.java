package edu.java.scrapper;

import edu.java.scrapper.entity.Chat;
import edu.java.scrapper.entity.ChatLinks;
import edu.java.scrapper.entity.Link;
import lombok.experimental.UtilityClass;
import java.util.Date;

@UtilityClass
public class TestConstants {

    public static final long TEST_ID = 123L;
    public static final long TEST_CHAT_ID = 12345L;
    public static final long TEST_LINK_ID = 54321L;
    public static final long TEST_TG_CHAT_ID = 777L;
    public static final String TEST_LINK_VALUE = "https://stackoverflow.com/questions/66675088/qt-copying-file-error-while-trying-to-run-the-project-how-to-fix-a-mistake-and";
    public static final String TEST_SHORT_NAME = "test_name";
    public static final Date TEST_CREATED_AT = new Date();

    public Chat chat() {
        Chat chat = new Chat();

        chat.setTgChatId(TEST_TG_CHAT_ID);
        chat.setCreatedAt(TEST_CREATED_AT);

        return chat;
    }

    public Chat chat1() {
        Chat chat1 = new Chat();

        chat1.setTgChatId(999L);
        chat1.setCreatedAt(new Date());

        return chat1;
    }

    public Link link() {
        Link link = new Link();

        link.setValue(TEST_LINK_VALUE);
        link.setHashsum(10000);

        return link;
    }

    public Link link1() {
        Link link1 = new Link();

        link1.setValue("https://github.com/projects/123");
        link1.setHashsum(123456);

        return link1;
    }

    public ChatLinks chatLinks(long chatId, long linkId) {
        ChatLinks chatLinks = new ChatLinks();

        chatLinks.setChatId(chatId);
        chatLinks.setLinkId(linkId);
        chatLinks.setShortName(TEST_SHORT_NAME);

        return chatLinks;
    }
}
