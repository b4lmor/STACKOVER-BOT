package edu.java.bot.core.communication.dialog;

import edu.java.bot.core.communication.command.Command;
import java.util.ArrayDeque;
import java.util.Queue;
import edu.java.bot.core.communication.command.impl.CommandSaveLink;
import edu.java.bot.core.communication.command.impl.CommandSendMessage;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TraceBotDialogs extends Dialog {

    public Dialog newAddLinkDialog() {

        Queue<Command> events = new ArrayDeque<>();

        var command = new CommandSendMessage("You have successfully subscribed!");
        command.setNext(new CommandSaveLink());

        events.add(command);

        return new Dialog(events);

    }
}
