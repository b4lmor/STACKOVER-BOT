package edu.java.bot.configuration;

import edu.java.bot.api.limiting.RateLimitBucketsCache;
import edu.java.bot.api.limiting.RateLimitInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RateLimitConfiguration implements WebMvcConfigurer {

    private final RateLimitBucketsCache rateLimitBucketsCache;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RateLimitInterceptor(rateLimitBucketsCache)).addPathPatterns("/**");
    }
}
