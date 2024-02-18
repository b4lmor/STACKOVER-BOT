package edu.java.bot.api.filter.chain;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.api.filter.BotFilter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BotFilterChain {

    private final List<BotFilter> filters;

    @Autowired
    public BotFilterChain(List<BotFilter> filters) {
        this.filters = filters.stream()
            .filter(BotFilter::isEnabled)
            .toList();
    }

    public void process(Update update) {
        filters.forEach(filter -> filter.doFilter(update));
    }

}
