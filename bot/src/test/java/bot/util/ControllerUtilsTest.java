package bot.util;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.api.controller.impl.CommandController;
import edu.java.bot.util.ControllerUtils;
import java.lang.reflect.Method;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ControllerUtilsTest {

    @SneakyThrows
    static Stream<Arguments> argumentsProvider() {
        return Stream.of(
            Arguments.of(
                "/start",
                CommandController.class.getDeclaredMethod("handleStart", Update.class)
            ),
            Arguments.of(
                "/list",
                CommandController.class.getDeclaredMethod("handleList", Update.class)
            ),
            Arguments.of(
                "/track",
                CommandController.class.getDeclaredMethod("handleTrack", Update.class)
            ),
            Arguments.of(
                "/untrack",
                CommandController.class.getDeclaredMethod("handleUntrack", Update.class)
            ),
            Arguments.of(
                "/help",
                CommandController.class.getDeclaredMethod("handleHelp", Update.class)
            ),
            Arguments.of(
                "/NoT-CommmmmANd&#^&*$@nAME",
                CommandController.class.getDeclaredMethod("handleOther", Update.class)
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
