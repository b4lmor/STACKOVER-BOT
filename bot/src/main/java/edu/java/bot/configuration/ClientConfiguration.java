package edu.java.bot.configuration;

import edu.java.bot.api.retrying.RetryFilter;
import edu.java.bot.api.retrying.backoff.Backoff;
import edu.java.bot.api.scrapper.client.ScrapperClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Configuration
public class ClientConfiguration {

    private final ApplicationConfig applicationConfig;

    @Bean
    public ScrapperClient scrapperClient() {
        ApplicationConfig.Retry retryConfig = applicationConfig.retry();
        Backoff backoff = retryConfig.backoffType().getBackoff(retryConfig.minBackoff());
        RetryFilter retryFilter = new RetryFilter(backoff, retryConfig.httpStatuses(), retryConfig.maxAttempts());
        return new ScrapperClient(retryFilter);
    }

}
