package edu.java.bot.api.filter.chain;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.api.filter.BotFilter;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BotFilterChain {

    private final PriorityQueue<BotFilter> filters;
    private final ApplicationContext ctx;

    @Autowired
    public BotFilterChain(ApplicationContext ctx) {
        this.ctx = ctx;
        this.filters = findFilters();
    }

    public void process(Update update) {
        filters.forEach(filter -> filter.doFilter(update));
    }

    private PriorityQueue<BotFilter> findFilters() {
        return ctx.getBeansOfType(BotFilter.class).values().stream()
            .filter(BotFilter::isEnabled)
            .collect(Collectors.toCollection(() ->
                new PriorityQueue<>(Comparator.comparingInt(filter ->
                    filter.getPriority().getValue())))
            );
    }
}
