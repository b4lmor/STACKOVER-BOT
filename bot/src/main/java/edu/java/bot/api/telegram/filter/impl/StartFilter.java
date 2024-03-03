package edu.java.bot.api.telegram.filter.impl;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.api.telegram.filter.ABotFilter;
import edu.java.bot.api.telegram.processor.impl.CommandBotProcessor;
import edu.java.bot.core.telegram.service.BotService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StartFilter extends ABotFilter {

    private final BotService botService;

    private final CommandBotProcessor commandBotProcessor;

    @Override
    public void doFilter(Update update) {
        if (!botService.isChatOpened(update)) {
            setNextFilter(null);
            commandBotProcessor.process(update);
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
