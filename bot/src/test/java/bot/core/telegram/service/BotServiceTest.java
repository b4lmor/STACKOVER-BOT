package bot.core.telegram.service;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import edu.java.bot.core.repository.LinkRepository;
import edu.java.bot.core.telegram.Bot;
import edu.java.bot.core.telegram.service.BotService;
import edu.java.bot.entity.Link;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BotServiceTest {

    LinkRepository linkRepository = new LinkRepository();
    Bot bot = mock(Bot.class);
    Link link = mock(Link.class);
    Update update = mock(Update.class);
    Message message = mock(Message.class);
    User user = mock(User.class);
    BotService botService = new BotService(bot, linkRepository);

    {
        when(update.message()).thenReturn(message);
        when(message.from()).thenReturn(user);
        when(user.id()).thenReturn(1L);
    }

    @Test
    public void getAllLinks() {
        var links = botService.getAllLinks(update);
        assertTrue(links.isEmpty());
    }

    @Test
    public void saveAndGetLink() {
        botService.saveLink(update, link);
        var links = botService.getAllLinks(update);
        assertIterableEquals(
            List.of(link),
            links
        );
    }

}
