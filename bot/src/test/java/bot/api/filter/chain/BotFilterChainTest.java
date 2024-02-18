package bot.api.filter.chain;

import edu.java.bot.api.filter.BotFilter;
import edu.java.bot.api.filter.chain.BotFilterChain;
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

    private final BotFilter filter1 = mock(BotFilter.class);
    private final BotFilter filter2 = mock(BotFilter.class);

    @BeforeEach
    void setUp() {

        List<BotFilter> filters = List.of(filter1, filter2);

        when(filter1.isEnabled()).thenReturn(true);
        when(filter2.isEnabled()).thenReturn(true);

        botFilterChain = new BotFilterChain(filters);
    }

    @Test
    @SneakyThrows
    void testFiltersInitialization() {
        Field filtersField = BotFilterChain.class.getDeclaredField("filters");
        filtersField.setAccessible(true);
        List<BotFilter> filters = (List<BotFilter>) filtersField.get(botFilterChain);

        Assertions.assertEquals(2, filters.size());
    }

}
