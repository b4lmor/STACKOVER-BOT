/*
 * This file is generated by jOOQ.
 */

package edu.java.scrapper.core.dao.jooq.generated;

import edu.java.scrapper.core.dao.jooq.generated.tables.ChatLinks;
import edu.java.scrapper.core.dao.jooq.generated.tables.Chats;
import edu.java.scrapper.core.dao.jooq.generated.tables.Links;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.NotNull;
import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.17.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class DefaultSchema extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>DEFAULT_SCHEMA</code>
     */
    public static final DefaultSchema DEFAULT_SCHEMA = new DefaultSchema();

    /**
     * The table <code>CHAT_LINKS</code>.
     */
    public final ChatLinks CHAT_LINKS = ChatLinks.CHAT_LINKS;

    /**
     * The table <code>CHATS</code>.
     */
    public final Chats CHATS = Chats.CHATS;

    /**
     * The table <code>LINKS</code>.
     */
    public final Links LINKS = Links.LINKS;

    /**
     * No further instances allowed
     */
    private DefaultSchema() {
        super("", null);
    }

    @Override
    @NotNull
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    @NotNull
    public final List<Table<?>> getTables() {
        return Arrays.asList(
            ChatLinks.CHAT_LINKS,
            Chats.CHATS,
            Links.LINKS
        );
    }
}