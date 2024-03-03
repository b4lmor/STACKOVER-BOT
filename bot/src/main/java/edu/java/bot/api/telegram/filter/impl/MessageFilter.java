package edu.java.bot.api.telegram.filter.impl;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.api.telegram.filter.ABotFilter;
import edu.java.bot.api.telegram.processor.impl.MessageBotProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import static edu.java.bot.api.telegram.Commands.COMMAND_PREFIX;

@Component
@Order(3)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageFilter extends ABotFilter {

    private final MessageBotProcessor botProcessor;

    @Override
    public void doFilter(Update update) {
        if (!update.message().text().startsWith(COMMAND_PREFIX)) {
            botProcessor.process(update);
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
