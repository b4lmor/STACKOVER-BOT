package edu.java.scrapper.db.jdbc.dao;

import edu.java.scrapper.TestConstants;
import edu.java.scrapper.core.dao.jdbc.JdbcChatDao;
import edu.java.scrapper.core.dao.jdbc.JdbcChatLinksDao;
import edu.java.scrapper.core.dao.jdbc.JdbcLinkDao;
import edu.java.scrapper.core.scheduled.LinkUpdaterScheduler;
import edu.java.scrapper.db.IntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class JdbcDaoTest extends IntegrationTest {

    @MockBean
    private LinkUpdaterScheduler linkUpdaterScheduler;

    @Autowired
    private JdbcChatDao chatDao;

    @Autowired
    private JdbcChatLinksDao chatLinksDao;

    @Autowired
    private JdbcLinkDao linkDao;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("app.database-access-type", () -> "jdbc");
    }

    @Test
    public void isJdbc() {
        Assertions.assertInstanceOf(JdbcChatDao.class, chatDao);
        Assertions.assertInstanceOf(JdbcLinkDao.class, linkDao);
        Assertions.assertInstanceOf(JdbcChatLinksDao.class, chatLinksDao);
    }

    @Test
    @Transactional
    @Rollback
    public void addChatTest() {
        chatDao.add(TestConstants.chat());
        chatDao.add(TestConstants.chat1());

        var chats = chatDao.findAll();

        Assertions.assertEquals(2, chats.size());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteChatTest() {
        chatDao.add(TestConstants.chat());
        chatDao.add(TestConstants.chat1());

        var chatsBefore = chatDao.findAll();

        chatDao.delete(chatsBefore.getFirst().getId());

        var chatsAfter = chatDao.findAll();

        Assertions.assertEquals(1, chatsBefore.size() - chatsAfter.size());
    }

    @Test
    @Transactional
    @Rollback
    public void findChatByTgChatIdTestSuccess() {
        chatDao.add(TestConstants.chat());

        var chat = chatDao.findByTgChatId(TestConstants.TEST_TG_CHAT_ID);

        Assertions.assertEquals(TestConstants.chat().getTgChatId(), chat.get().getTgChatId());
    }

    @Test
    @Transactional
    @Rollback
    public void findChatByTgChatIdTestFail() {
        var chat = chatDao.findByTgChatId(TestConstants.TEST_TG_CHAT_ID);

        Assertions.assertTrue(chat.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void addChatLinksTest() {
        chatDao.add(TestConstants.chat());
        linkDao.add(TestConstants.link());

        var chat = chatDao.findAll().getFirst();
        var link = linkDao.findAll().getFirst();

        chatLinksDao.add(TestConstants.chatLinks(chat.getId(), link.getId()));

        var chatLinks = chatLinksDao.findAll().getFirst();

        Assertions.assertEquals(1, chatLinks.getLinkId());
        Assertions.assertEquals(1, chatLinks.getChatId());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteChatLinksTest() {
        chatDao.add(TestConstants.chat());
        linkDao.add(TestConstants.link());

        var chat = chatDao.findAll().getFirst();
        var link = linkDao.findAll().getFirst();

        chatLinksDao.add(TestConstants.chatLinks(chat.getId(), link.getId()));

        var chatLinksBefore = chatLinksDao.findAll();

        chatLinksDao.delete(chatLinksBefore.getFirst().getId());

        var chatLinksAfter = chatLinksDao.findAll();

        Assertions.assertEquals(1, chatLinksBefore.size() - chatLinksAfter.size());
    }

    @Test
    @Transactional
    @Rollback
    public void findAllChatsConnectedWithLinkTest() {
        chatDao.add(TestConstants.chat());
        chatDao.add(TestConstants.chat1());
        linkDao.add(TestConstants.link());
        linkDao.add(TestConstants.link1());

        var chat = chatDao.findAll().get(0);
        var chat1 = chatDao.findAll().get(1);
        var link = linkDao.findAll().get(0);
        var link1 = linkDao.findAll().get(1);

        chatLinksDao.add(TestConstants.chatLinks(chat.getId(), link.getId()));
        chatLinksDao.add(TestConstants.chatLinks(chat1.getId(), link1.getId()));

        var chats = chatLinksDao.findAllChatsConnectedWithLink(TestConstants.TEST_LINK_VALUE);

        Assertions.assertEquals(1, chats.size());
        Assertions.assertEquals(TestConstants.chat().getTgChatId(), chats.getFirst().getTgChatId());
    }

    @Test
    @Transactional
    @Rollback
    public void findAllLinksConnectedWithChat() {
        chatDao.add(TestConstants.chat());
        chatDao.add(TestConstants.chat1());
        linkDao.add(TestConstants.link());
        linkDao.add(TestConstants.link1());

        var chat = chatDao.findAll().get(0);
        var chat1 = chatDao.findAll().get(1);
        var link = linkDao.findAll().get(0);
        var link1 = linkDao.findAll().get(1);

        chatLinksDao.add(TestConstants.chatLinks(chat.getId(), link.getId()));
        chatLinksDao.add(TestConstants.chatLinks(chat1.getId(), link1.getId()));

        var links = chatLinksDao.findAllLinksConnectedWithChat(TestConstants.TEST_TG_CHAT_ID);

        Assertions.assertEquals(1, links.size());
        Assertions.assertEquals(TestConstants.link().getLvalue(), links.getFirst().getLvalue());
    }

    @Test
    @Transactional
    @Rollback
    public void addLinkTest() {
        linkDao.add(TestConstants.link());
        linkDao.add(TestConstants.link1());

        var links = linkDao.findAll();

        Assertions.assertEquals(2, links.size());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteLinkTest() {
        linkDao.add(TestConstants.link());
        linkDao.add(TestConstants.link1());

        var linksBefore = linkDao.findAll();

        linkDao.delete(linksBefore.getFirst().getId());

        var linksAfter = linkDao.findAll();

        Assertions.assertEquals(1, linksBefore.size() - linksAfter.size());
    }

    @Test
    @Transactional
    @Rollback
    public void findLinkByValueTestSuccess() {
        linkDao.add(TestConstants.link());

        var link = linkDao.findByValue(TestConstants.TEST_LINK_VALUE);

        Assertions.assertEquals(TestConstants.link().getLvalue(), link.get().getLvalue());
    }

    @Test
    @Transactional
    @Rollback
    public void findLinkByValueTestFail() {
        var link = linkDao.findByValue(TestConstants.TEST_LINK_VALUE);

        Assertions.assertTrue(link.isEmpty());
    }

}
