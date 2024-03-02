package bot.util;

import edu.java.bot.entity.Link;
import edu.java.bot.util.PrettifyUtils;
import java.util.List;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PrettifyUtilsTest {

    @SneakyThrows
    static Stream<Arguments> argumentsProvider() {
        return Stream.of(
            Arguments.of(
                List.of(
                    new Link(
                        "https://stackoverflow.com/questions/66675088/qt-copying-file-error-while-trying-to-run-the-project-how-to-fix-a-mistake-and",
                        "name1"
                    ),
                    new Link(
                        "https://github.com/pengrad/java-telegram-bot-api",
                        "name2"
                    )
                ),
                """
                List of tracked resources:

                1. <a href=\"https://stackoverflow.com/questions/66675088/qt-copying-file-error-while-trying-to-run-the-project-how-to-fix-a-mistake-and\">name1</a>
                • Resource: [stackoverflow.com]

                2. <a href=\"https://github.com/pengrad/java-telegram-bot-api\">name2</a>
                • Resource: [github.com]

                """
            ),
            Arguments.of(
                List.of(),
                "List of tracked resources is empty! Send /track to add one."
            )
        );
    }

    @ParameterizedTest
    @MethodSource("argumentsProvider")
    public void prettifyLinks(List<Link> links, String expectedResult) {
        Assertions.assertEquals(
            expectedResult,
            PrettifyUtils.prettifyLinks(links)
        );
    }

}
