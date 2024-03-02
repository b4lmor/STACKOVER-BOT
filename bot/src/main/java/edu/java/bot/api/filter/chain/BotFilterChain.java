package edu.java.bot.api.filter.chain;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.api.filter.BotFilter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BotFilterChain {

    private final List<BotFilter> filters;

    public void process(Update update) {
        filters.stream()
            .filter(BotFilter::isEnabled)
            .forEach(filter -> filter.doFilter(update));
    }

}
