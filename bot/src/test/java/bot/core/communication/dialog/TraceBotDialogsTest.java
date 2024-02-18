package bot.core.communication.dialog;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import edu.java.bot.core.communication.command.Command;
import edu.java.bot.core.communication.dialog.Dialog;
import edu.java.bot.core.communication.dialog.TraceBotDialogs;
import edu.java.bot.core.repository.LinkRepository;
import edu.java.bot.core.telegram.TraceBot;
import edu.java.bot.core.telegram.service.BotService;
import edu.java.bot.entity.Link;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TraceBotDialogsTest {

    TraceBot bot = mock(TraceBot.class);
    Update update = mock(Update.class);
    Message message = mock(Message.class);
    User user = mock(User.class);

    LinkRepository linkRepository = new LinkRepository();

    BotService botService = new BotService(bot, linkRepository);

    {
        when(update.message()).thenReturn(message);
        when(message.from()).thenReturn(user);
        when(message.text()).thenReturn("https://github.com/pengrad/java-telegram-bot-api");
        when(user.id()).thenReturn(1L);
    }

    @Test
    public void newRemoveLinkDialog() {

        var link = new Link(
            "https://github.com/pengrad/java-telegram-bot-api",
            "name1"
        );

        linkRepository.save(1L, link);

        Dialog dialog = TraceBotDialogs.newRemoveLinkDialog();

        Command command = dialog.pop();

        when(message.text()).thenReturn("name1");
        try {
            command.execute(botService, update);
        } catch (RuntimeException ignored) {
        }

        assertTrue(linkRepository.getAll(1L).isEmpty());
    }

    @Test
    public void newAddLinkDialog() {

        var link = new Link(
            "https://github.com/pengrad/java-telegram-bot-api",
            "name1"
        );

        Dialog dialog = TraceBotDialogs.newAddLinkDialog();

        Command command = dialog.pop();

        when(message.text()).thenReturn("name1");
        try {
            command.execute(botService, update);
        } catch (RuntimeException ignored) {
        }

        command = dialog.pop();

        when(message.text()).thenReturn("https://github.com/pengrad/java-telegram-bot-api");
        try {
            command.execute(botService, update);
        } catch (RuntimeException ignored) {
        }

        assertTrue(linkRepository.getAll(1L).getFirst().equals(link));
    }

}
