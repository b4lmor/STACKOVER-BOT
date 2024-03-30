package edu.java.bot.api.retrying.backoff;

import java.time.Duration;

public enum BackoffType {

    CONSTANT,

    LINEAR,

    EXPONENTIAL;

    public Backoff getBackoff(Duration minBackoff) {
        return switch (this) {
            case CONSTANT -> new ConstantBackoff(minBackoff);
            case LINEAR -> new LinearBackoff(minBackoff);
            case EXPONENTIAL -> new ExponentialBackoff(minBackoff);
        };
    }

}
