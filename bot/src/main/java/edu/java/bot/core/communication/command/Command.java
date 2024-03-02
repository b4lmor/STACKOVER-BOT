package edu.java.bot.core.communication.command;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.core.telegram.service.BotService;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class Command {

    private Command nextIfFailed;
    private Command nextIfSuccess;

    protected abstract boolean start(BotService botService, Update update);

    public boolean execute(BotService botService, Update update) {
        boolean res = start(botService, update);
        if (res) {
            return nextIfSuccess == null
                ? res
                : nextIfSuccess.execute(botService, update);
        } else {
            return nextIfFailed == null
                ? res
                : nextIfFailed.execute(botService, update);
        }
    }

}
