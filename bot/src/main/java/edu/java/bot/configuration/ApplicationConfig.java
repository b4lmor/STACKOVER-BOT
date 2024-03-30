package edu.java.bot.configuration;

import edu.java.bot.api.retrying.backoff.BackoffType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.Duration;
import java.util.Set;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(

    @NotEmpty
    String telegramToken,

    @NotNull
    Retry retry,

    @Bean
    @NotNull
    RateLimit rateLimit

) {

    public record RateLimit(

        Long capacity,

        Long refillRate,

        Long refillTimeSeconds,

        Long cacheSize,

        Duration expireAfterAccess

    ) {
    }

    public record Retry(

        @NotNull
        @PositiveOrZero
        Integer maxAttempts,

        @NotNull
        Duration minBackoff,

        @NotNull
        BackoffType backoffType,

        @NotEmpty
        Set<HttpStatus> httpStatuses

    ) {
    }

}
