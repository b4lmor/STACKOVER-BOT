package edu.java.scrapper.configuration;

import edu.java.scrapper.core.dao.jdbc.JdbcChatDao;
import edu.java.scrapper.core.dao.jdbc.JdbcChatLinksDao;
import edu.java.scrapper.core.dao.jdbc.JdbcLinkDao;
import edu.java.scrapper.core.dao.jooq.JooqChatDao;
import edu.java.scrapper.core.dao.jooq.JooqChatLinksDao;
import edu.java.scrapper.core.dao.jooq.JooqLinkDao;
import edu.java.scrapper.core.dao.jpa.JpaChatDao;
import edu.java.scrapper.core.dao.jpa.JpaChatLinksDao;
import edu.java.scrapper.core.dao.jpa.JpaLinkDao;
import edu.java.scrapper.core.service.ChatService;
import edu.java.scrapper.core.service.LinkService;
import edu.java.scrapper.core.service.impl.DefaultChatService;
import edu.java.scrapper.core.service.impl.DefaultLinkService;
import edu.java.scrapper.core.service.impl.JpaChatService;
import edu.java.scrapper.core.service.impl.JpaLinkService;
import lombok.RequiredArgsConstructor;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DataAccessConfiguration {

    private final JdbcChatDao jdbcChatDao;

    private final JdbcLinkDao jdbcLinkDao;

    private final JdbcChatLinksDao jdbcChatLinksDao;

    private final JooqLinkDao jooqLinkDao;

    private final JooqChatDao jooqChatDao;

    private final JooqChatLinksDao jooqChatLinksDao;

    private final JpaChatDao jpaChatDao;

    private final JpaLinkDao jpaLinkDao;

    private final JpaChatLinksDao jpaChatLinksDao;

    @Bean
    @ConditionalOnProperty(name = "app.database-access-type", havingValue = "jdbc")
    public ChatService jdbcChatService() {
        return new DefaultChatService(jdbcChatDao);
    }

    @Bean
    @ConditionalOnProperty(name = "app.database-access-type", havingValue = "jdbc")
    public LinkService jdbcLinkService() {
        return new DefaultLinkService(jdbcLinkDao, jdbcChatDao, jdbcChatLinksDao, jdbcChatService());
    }

    @Bean
    @ConditionalOnProperty(name = "app.database-access-type", havingValue = "jooq")
    public ChatService jooqChatService() {
        return new DefaultChatService(jooqChatDao);
    }

    @Bean
    @ConditionalOnProperty(name = "app.database-access-type", havingValue = "jooq")
    public LinkService jooqLinkService() {
        return new DefaultLinkService(jooqLinkDao, jooqChatDao, jooqChatLinksDao, jdbcChatService());
    }

    @Bean
    @ConditionalOnProperty(name = "app.database-access-type", havingValue = "jpa")
    public ChatService jpaChatService() {
        return new JpaChatService(jpaChatDao);
    }

    @Bean
    @ConditionalOnProperty(name = "app.database-access-type", havingValue = "jpa")
    public LinkService jpaLinkService() {
        return new JpaLinkService(jpaChatDao, jpaLinkDao, jpaChatLinksDao, jpaChatService());
    }

    @Bean
    @ConditionalOnProperty(name = "app.database-access-type", havingValue = "jooq")
    public DefaultConfigurationCustomizer postgresJooqCustomizer() {
        return (DefaultConfiguration c) -> c.settings()
            .withRenderSchema(false)
            .withRenderFormatted(true)
            .withRenderQuotedNames(RenderQuotedNames.NEVER);
    }

    public enum DatabaseAccessType {
        JDBC,

        JOOQ,

        JPA
    }

}
