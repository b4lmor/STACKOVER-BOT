package edu.java.bot.api.retrying.backoff;

import java.time.Duration;

public interface Backoff {

    Duration calculateWaitTime(int attempt);

}
