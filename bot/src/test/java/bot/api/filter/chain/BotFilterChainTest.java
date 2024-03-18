package bot.api.filter.chain;

import edu.java.bot.api.telegram.filter.ABotFilter;
import edu.java.bot.api.telegram.filter.chain.BotFilterChain;
import java.lang.reflect.Field;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BotFilterChainTest {

    private BotFilterChain botFilterChain;

    private final ABotFilter filter1 = mock(ABotFilter.class);
    private final ABotFilter filter2 = mock(ABotFilter.class);

    @BeforeEach
    void setUp() {

        List<ABotFilter> filters = List.of(filter1, filter2);

        when(filter1.isEnabled()).thenReturn(true);
        when(filter2.isEnabled()).thenReturn(true);

        botFilterChain = new BotFilterChain(filters);
    }

    @Test
    @SneakyThrows
    void testFiltersInitialization() {
        Field filtersField = BotFilterChain.class.getDeclaredField("filters");
        filtersField.setAccessible(true);
        List<ABotFilter> filters = (List<ABotFilter>) filtersField.get(botFilterChain);

        Assertions.assertEquals(2, filters.size());
    }

}
