package bot.api.controller.impl;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.api.telegram.processor.impl.CommandBotProcessor;
import edu.java.bot.core.telegram.service.BotService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CommandBotProcessorTest {

    @Test
    public void handleStart() {
        Assertions.assertDoesNotThrow(() -> {
            Update update = mock(Update.class);
            Message message = mock(Message.class);
            BotService botService = mock(BotService.class);

            when(update.message()).thenReturn(message);
            when(message.text()).thenReturn("/start");
            doNothing().when(botService).sendMessage("Start command", update, null);

            CommandBotProcessor controller = new CommandBotProcessor(botService);
            controller.process(update);
        });
    }

    @Test
    public void handleHelp() {
        Assertions.assertDoesNotThrow(() -> {
            Update update = mock(Update.class);
            Message message = mock(Message.class);
            BotService botService = mock(BotService.class);

            when(update.message()).thenReturn(message);
            when(message.text()).thenReturn("/help");
            doNothing().when(botService).sendMessage("Help command", update, null);

            CommandBotProcessor controller = new CommandBotProcessor(botService);
            controller.process(update);
        });
    }

}
