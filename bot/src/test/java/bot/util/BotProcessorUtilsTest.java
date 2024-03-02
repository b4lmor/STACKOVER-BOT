package bot.util;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.api.controller.impl.CommandBotProcessor;
import edu.java.bot.util.ControllerUtils;
import java.lang.reflect.Method;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class BotProcessorUtilsTest {

    @SneakyThrows
    static Stream<Arguments> argumentsProvider() {
        return Stream.of(
            Arguments.of(
                "/start",
                CommandBotProcessor.class.getDeclaredMethod("handleStart", Update.class)
            ),
            Arguments.of(
                "/list",
                CommandBotProcessor.class.getDeclaredMethod("handleList", Update.class)
            ),
            Arguments.of(
                "/track",
                CommandBotProcessor.class.getDeclaredMethod("handleTrack", Update.class)
            ),
            Arguments.of(
                "/untrack",
                CommandBotProcessor.class.getDeclaredMethod("handleUntrack", Update.class)
            ),
            Arguments.of(
                "/help",
                CommandBotProcessor.class.getDeclaredMethod("handleHelp", Update.class)
            ),
            Arguments.of(
                "/NoT-CommmmmANd&#^&*$@nAME",
                CommandBotProcessor.class.getDeclaredMethod("handleOther", Update.class)
            )
        );
    }

    @ParameterizedTest
    @MethodSource("argumentsProvider")
    public void findHandler(String command, Method expectedResult) {
        Assertions.assertEquals(
            expectedResult,
            ControllerUtils.findHandler(command, "handleOther")
        );
    }

}
