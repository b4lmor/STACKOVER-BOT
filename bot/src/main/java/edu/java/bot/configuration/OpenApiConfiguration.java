package edu.java.bot.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OpenApiConfiguration {

    private final MessageSource messageSource;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(
                new Info()
                    .title(resolveProperty("api.title"))
                    .description(resolveProperty("api.description"))
                    .version("v1.0.0")
                    .license(
                        new License()
                            .name(resolveProperty("api.license"))
                            .url("https://github.com/b4lmor")
                    )
            );
    }

    private String resolveProperty(String property) {
        return messageSource.getMessage(property, null, Locale.getDefault());
    }

}
