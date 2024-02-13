package edu.java.bot.api.filter;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum FilterPriority {

    HIGH(2),

    MEDIUM(1),

    LOW(0);

    private final int value;

}
