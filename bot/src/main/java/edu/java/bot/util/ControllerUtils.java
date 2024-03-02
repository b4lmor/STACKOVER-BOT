package edu.java.bot.util;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.api.controller.impl.CommandBotProcessor;
import edu.java.bot.util.annotation.BotHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ControllerUtils {

    public List<Method> findMethods(Class<?> clazz, List<Predicate<Method>> filters) {
        return Arrays.stream(clazz.getDeclaredMethods()).filter(method ->
            filters.stream().allMatch(filter -> filter.test(method))
        ).toList();
    }

    public Method findHandler(String command, String defaultMethodName) {
        Predicate<Method> isHandler = method ->
            method.isAnnotationPresent(BotHandler.class)
                && method.getAnnotation(BotHandler.class).value().equals(command);
        try {
            return ControllerUtils.findMethods(CommandBotProcessor.class, List.of(isHandler)).getFirst();
        } catch (NoSuchElementException e) {
            try {
                return CommandBotProcessor.class.getDeclaredMethod(defaultMethodName, Update.class);
            } catch (NoSuchMethodException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
