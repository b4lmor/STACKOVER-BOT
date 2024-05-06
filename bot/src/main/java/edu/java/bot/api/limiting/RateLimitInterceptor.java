package edu.java.bot.api.limiting;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Log4j2
public class RateLimitInterceptor implements HandlerInterceptor {

    private final RateLimitBucketsCache cache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {

        Bucket tokenBucket = cache.getBucket(request.getRemoteAddr());
        ConsumptionProbe probe = tokenBucket.tryConsumeAndReturnRemaining(1);

        log.info(
            "User with IPv6 `{}` has `{}` remaining tokens",
            request.getRemoteAddr(),
            probe.getRemainingTokens()
        );

        if (probe.isConsumed()) {
            long remainingTokens = probe.getRemainingTokens();
            response.addHeader("X-Rate-Limit-Remaining", String.valueOf(remainingTokens));
            return true;
        }

        Duration waitDuration = Duration.ofNanos(probe.getNanosToWaitForRefill());
        response.addHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitDuration.toSeconds()));

        response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), "You have exhausted your API request quota");

        return false;
    }
}
