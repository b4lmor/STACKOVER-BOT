package edu.java.bot.configuration;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfiguration {

    public static final String TG_MESSAGES_COUNTER_METRIC = "tg_messages_received";

    @Bean
    MeterBinder meterBinder() {
        return registry -> Counter.builder(TG_MESSAGES_COUNTER_METRIC)
            .description("Кол-во обработанных сообщений из ТГ")
            .register(registry);
    }

}
