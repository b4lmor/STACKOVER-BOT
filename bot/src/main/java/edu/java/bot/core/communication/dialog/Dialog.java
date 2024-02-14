package edu.java.bot.core.communication.dialog;

import edu.java.bot.core.communication.command.Command;
import java.util.ArrayDeque;
import java.util.Queue;

public class Dialog {

    private final Queue<Command> events;

    public Dialog() {
        this.events = new ArrayDeque<>();
    }

    public Dialog(Queue<Command> events) {
        this.events = events;
    }

    public boolean isEmpty() {
        return events.isEmpty();
    }

    public Command pop() {
        return events.poll();
    }

    public void push(Command command) {
        events.add(command);
    }

}
