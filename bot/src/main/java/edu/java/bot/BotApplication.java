package edu.java.bot;

import edu.java.bot.api.filter.chain.BotFilterChain;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.core.telegram.TraceBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class BotApplication {

    public static void main(String[] args) {
        var ctx = SpringApplication.run(BotApplication.class, args);
        var bot = ctx.getBean(TraceBot.class);
        var filterChain = ctx.getBean(BotFilterChain.class);
        bot.run(filterChain);
    }

}
