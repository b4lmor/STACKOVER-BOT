package edu.java.bot.api.telegram.filter.chain;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.api.telegram.filter.ABotFilter;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BotFilterChain {

    private final List<ABotFilter> filters;

    @PostConstruct
    public void configureFilterChain() {
        IntStream.range(0, filters.size() - 1)
            .forEach(
                i -> filters.get(i)
                    .setNextFilter(filters.get(i + 1))
            );
    }

    public void process(Update update) {
        if (!filters.isEmpty()) {
            filters.getFirst().doFilter(update);
        }
    }

}
