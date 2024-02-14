package edu.java.bot.core.exception;

import org.apache.logging.log4j.message.FormattedMessage;

public class BotException extends RuntimeException {

    public BotException(String type, String message) {
        super(new FormattedMessage("Type: {}, message: {}.", type, message).toString());
    }

}
