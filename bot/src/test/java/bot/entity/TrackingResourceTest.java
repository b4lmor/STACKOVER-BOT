package bot.entity;

import edu.java.bot.entity.TrackingResource;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TrackingResourceTest {

    static Stream<Arguments> argumentsProvider() {
        return Stream.of(
            Arguments.of(
                "https://stackoverflow.com/questions/66675088/qt-copying-file-error-while-trying-to-run-the-project-how-to-fix-a-mistake-and",
                TrackingResource.STACKOVERFLOW
            ),
            Arguments.of(
                "https://github.com/pengrad/java-telegram-bot-api",
                TrackingResource.GITHUB
            ),
            Arguments.of(
                "http://github.com/pengrad/java-telegram-bot-api",
                TrackingResource.GITHUB
            ),
            Arguments.of(
                "github.com/pengrad/java-telegram-bot-api",
                TrackingResource.GITHUB
            )
        );
    }

    @ParameterizedTest
    @MethodSource("argumentsProvider")
    public void parseResource(String link, TrackingResource expectedResult) {
        Assertions.assertEquals(
            expectedResult,
            TrackingResource.parseResource(link)
        );
    }

}
