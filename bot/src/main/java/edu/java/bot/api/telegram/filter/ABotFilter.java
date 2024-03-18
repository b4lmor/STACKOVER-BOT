package edu.java.bot.api.telegram.filter;

import com.pengrad.telegrambot.model.Update;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class ABotFilter {

    protected ABotFilter nextFilter;

    public void doFilter(Update update) {
        if (nextFilter != null) {
            nextFilter.doFilter(update);
        }
    }

    public boolean isEnabled() {
        return false;
    }

}
