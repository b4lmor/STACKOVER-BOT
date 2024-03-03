package edu.java.bot.api.telegram;

import com.pengrad.telegrambot.model.BotCommand;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Commands {

    public static final String COMMAND_PREFIX = "/";

    public static final String START_COMMAND = COMMAND_PREFIX + "start";

    public static final String HELP_COMMAND = COMMAND_PREFIX + "help";

    public static final String LIST_COMMAND = COMMAND_PREFIX + "list";

    public static final String TRACK_COMMAND = COMMAND_PREFIX + "track";

    public static final String UNTRACK_COMMAND = COMMAND_PREFIX + "untrack";

    public static BotCommand[] getCommands() {
        return new BotCommand[]{
            new BotCommand(
                START_COMMAND,
                "-- Register a new user"
            ),
            new BotCommand(
                HELP_COMMAND,
                "-- Get some advices"
            ),
            new BotCommand(
                LIST_COMMAND,
                "-- Show list of tracked links"
            ),
            new BotCommand(
                TRACK_COMMAND,
                "-- Start tracking a link"
            ),
            new BotCommand(
                UNTRACK_COMMAND,
                "-- Stop tracking a link"
            ),
        };
    }

}
