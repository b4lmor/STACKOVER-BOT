package edu.java.bot.api.telegram.filter.impl;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import edu.java.bot.api.telegram.filter.ABotFilter;
import edu.java.bot.api.telegram.processor.impl.CommandBotProcessor;
import edu.java.bot.core.telegram.service.BotService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import static edu.java.bot.api.telegram.Commands.START_COMMAND;

@Component
@Order(1)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StartFilter extends ABotFilter {

    private final BotService botService;

    private final CommandBotProcessor commandBotProcessor;

    @Override
    public void doFilter(Update update) {
        if (START_COMMAND.equals(update.message().text())) {
            commandBotProcessor.process(update);

        } else if (!botService.isChatOpened(update)) {
            setNextFilter(null);
            botService.sendMessage(
                "Your chat isn't active yet! Type " + START_COMMAND + " to begin.",
                update,
                ParseMode.Markdown
            );
        }
        if (nextFilter != null) {
            nextFilter.doFilter(update);
        }

    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
