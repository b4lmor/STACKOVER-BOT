package edu.java.scrapper.configuration;

import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(

    @NotNull
    Scheduler scheduler

) {

    @Bean
    public Duration updateInterval() {
        return scheduler.updateInterval;
    }

    @Bean
    public Duration deleteInterval() {
        return scheduler.updateInterval;
    }

    public record Scheduler(@NotNull Duration updateInterval, @NotNull Duration deleteInterval) {
    }

}
