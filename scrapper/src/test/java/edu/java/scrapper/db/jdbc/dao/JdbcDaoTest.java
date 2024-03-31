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
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class JdbcDaoTest extends IntegrationTest {

    @MockBean
    private LinkUpdaterScheduler linkUpdaterScheduler;

    @Autowired
    private JdbcChatDao jdbcChatDao;

    @Autowired
    private JdbcChatLinksDao jdbcChatLinksDao;

    @Autowired
    private JdbcLinkDao jdbcLinkDao;

    @Test
    @Transactional
    @Rollback
    public void addChatTest() {
        jdbcChatDao.add(TestConstants.chat());
        jdbcChatDao.add(TestConstants.chat1());

        var chats = jdbcChatDao.findAll();

        Assertions.assertEquals(2, chats.size());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteChatTest() {
        jdbcChatDao.add(TestConstants.chat());
        jdbcChatDao.add(TestConstants.chat1());

        var chatsBefore = jdbcChatDao.findAll();

        jdbcChatDao.delete(chatsBefore.getFirst().getId());

        var chatsAfter = jdbcChatDao.findAll();

        Assertions.assertEquals(1, chatsBefore.size() - chatsAfter.size());
    }

    @Test
    @Transactional
    @Rollback
    public void findChatByTgChatIdTestSuccess() {
        jdbcChatDao.add(TestConstants.chat());

        var chat = jdbcChatDao.findByTgChatId(TestConstants.TEST_TG_CHAT_ID);

        Assertions.assertEquals(TestConstants.chat().getTgChatId(), chat.get().getTgChatId());
    }

    @Test
    @Transactional
    @Rollback
    public void findChatByTgChatIdTestFail() {
        var chat = jdbcChatDao.findByTgChatId(TestConstants.TEST_TG_CHAT_ID);

        Assertions.assertTrue(chat.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void addChatLinksTest() {
        jdbcChatDao.add(TestConstants.chat());
        jdbcLinkDao.add(TestConstants.link());

        var chat = jdbcChatDao.findAll().getFirst();
        var link = jdbcLinkDao.findAll().getFirst();

        jdbcChatLinksDao.add(TestConstants.chatLinks(chat.getId(), link.getId()));

        var chatLinks = jdbcChatLinksDao.findAll().getFirst();

        Assertions.assertEquals(1, chatLinks.getLinkId());
        Assertions.assertEquals(1, chatLinks.getChatId());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteChatLinksTest() {
        jdbcChatDao.add(TestConstants.chat());
        jdbcLinkDao.add(TestConstants.link());

        var chat = jdbcChatDao.findAll().getFirst();
        var link = jdbcLinkDao.findAll().getFirst();

        jdbcChatLinksDao.add(TestConstants.chatLinks(chat.getId(), link.getId()));

        var chatLinksBefore = jdbcChatLinksDao.findAll();

        jdbcChatLinksDao.delete(chatLinksBefore.getFirst().getId());

        var chatLinksAfter = jdbcChatLinksDao.findAll();

        Assertions.assertEquals(1, chatLinksBefore.size() - chatLinksAfter.size());
    }

    @Test
    @Transactional
    @Rollback
    public void findAllChatsConnectedWithLinkTest() {
        jdbcChatDao.add(TestConstants.chat());
        jdbcChatDao.add(TestConstants.chat1());
        jdbcLinkDao.add(TestConstants.link());
        jdbcLinkDao.add(TestConstants.link1());

        var chat = jdbcChatDao.findAll().get(0);
        var chat1 = jdbcChatDao.findAll().get(1);
        var link = jdbcLinkDao.findAll().get(0);
        var link1 = jdbcLinkDao.findAll().get(1);

        jdbcChatLinksDao.add(TestConstants.chatLinks(chat.getId(), link.getId()));
        jdbcChatLinksDao.add(TestConstants.chatLinks(chat1.getId(), link1.getId()));

        var chats = jdbcChatLinksDao.findAllChatsConnectedWithLink(TestConstants.TEST_LINK_VALUE);

        Assertions.assertEquals(1, chats.size());
        Assertions.assertEquals(TestConstants.chat().getTgChatId(), chats.getFirst().getTgChatId());
    }

    @Test
    @Transactional
    @Rollback
    public void findAllLinksConnectedWithChat() {
        jdbcChatDao.add(TestConstants.chat());
        jdbcChatDao.add(TestConstants.chat1());
        jdbcLinkDao.add(TestConstants.link());
        jdbcLinkDao.add(TestConstants.link1());

        var chat = jdbcChatDao.findAll().get(0);
        var chat1 = jdbcChatDao.findAll().get(1);
        var link = jdbcLinkDao.findAll().get(0);
        var link1 = jdbcLinkDao.findAll().get(1);

        jdbcChatLinksDao.add(TestConstants.chatLinks(chat.getId(), link.getId()));
        jdbcChatLinksDao.add(TestConstants.chatLinks(chat1.getId(), link1.getId()));

        var links = jdbcChatLinksDao.findAllLinksConnectedWithChat(TestConstants.TEST_TG_CHAT_ID);

        Assertions.assertEquals(1, links.size());
        Assertions.assertEquals(TestConstants.link().getLvalue(), links.getFirst().getLvalue());
    }

    @Test
    @Transactional
    @Rollback
    public void addLinkTest() {
        jdbcLinkDao.add(TestConstants.link());
        jdbcLinkDao.add(TestConstants.link1());

        var links = jdbcLinkDao.findAll();

        Assertions.assertEquals(2, links.size());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteLinkTest() {
        jdbcLinkDao.add(TestConstants.link());
        jdbcLinkDao.add(TestConstants.link1());

        var linksBefore = jdbcLinkDao.findAll();

        jdbcLinkDao.delete(linksBefore.getFirst().getId());

        var linksAfter = jdbcLinkDao.findAll();

        Assertions.assertEquals(1, linksBefore.size() - linksAfter.size());
    }

    @Test
    @Transactional
    @Rollback
    public void findLinkByValueTestSuccess() {
        jdbcLinkDao.add(TestConstants.link());

        var link = jdbcLinkDao.findByValue(TestConstants.TEST_LINK_VALUE);

        Assertions.assertEquals(TestConstants.link().getLvalue(), link.get().getLvalue());
    }

    @Test
    @Transactional
    @Rollback
    public void findLinkByValueTestFail() {
        var link = jdbcLinkDao.findByValue(TestConstants.TEST_LINK_VALUE);

        Assertions.assertTrue(link.isEmpty());
    }

}
