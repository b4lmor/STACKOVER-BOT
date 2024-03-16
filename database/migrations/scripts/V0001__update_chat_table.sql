ALTER TABLE chats
    ADD COLUMN createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE chats
    ADD COLUMN chat_id BIGSERIAL UNIQUE NOT NULL;

ALTER TABLE chats RENAME COLUMN createdAt TO created_at;

ALTER TABLE chats RENAME COLUMN createdBy TO created_by;

ALTER TABLE chats RENAME COLUMN chat_id TO tg_chat_id;

ALTER TABLE chats
    ADD COLUMN is_active BOOLEAN DEFAULT false;

CREATE INDEX index_links_value ON links(value);

CREATE INDEX index_chats_tg_chat_id ON chats(tg_chat_id);

ALTER TABLE links
    ADD CONSTRAINT unique_value
        UNIQUE (value);

ALTER TABLE chats
    ADD CONSTRAINT unique_tg_chat_id
        UNIQUE (tg_chat_id);

ALTER TABLE chat_links
    ADD CONSTRAINT unique_id_pair
        UNIQUE (chat_id, link_id);

ALTER TABLE links
    ADD COLUMN last_update_at DATE DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE links
    ADD short_name VARCHAR(40) NOT NULL default 'Your link' ;
CREATE INDEX index_links_short_name ON links(short_name);

ALTER TABLE chats
    DROP COLUMN created_by;

ALTER TABLE links
    DROP COLUMN short_name;

ALTER TABLE chat_links
    ADD short_name VARCHAR(40) NOT NULL default 'Your link' ;
