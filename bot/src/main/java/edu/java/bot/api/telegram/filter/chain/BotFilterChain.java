package edu.java.bot.api.telegram.filter.chain;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.api.telegram.filter.ABotFilter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static edu.java.bot.configuration.MetricsConfiguration.TG_MESSAGES_COUNTER_METRIC;

@Component
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BotFilterChain {

    private final List<ABotFilter> filters;

    @Nullable
    private final MeterRegistry meterRegistry;

    @PostConstruct
    public void configureFilterChain() {
        log.info("[FILTER CHAIN] :: Number of filters: {}.", filters.size());
        IntStream.range(0, filters.size() - 1).forEach(i -> filters.get(i).setNextFilter(filters.get(i + 1)));
    }

    public void process(Update update) {
        if (meterRegistry != null) {
            meterRegistry.counter(TG_MESSAGES_COUNTER_METRIC, List.of()).increment();
        }
        if (!filters.isEmpty()) {
            filters.getFirst().doFilter(update);
        }
    }

}
