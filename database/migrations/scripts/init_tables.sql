CREATE TABLE chats
(
    id        BIGSERIAL PRIMARY KEY,
    createdBy VARCHAR(50) NOT NULL
);

CREATE TABLE links
(
    id      BIGSERIAL PRIMARY KEY,
    lvalue   VARCHAR(400) NOT NULL,
    hashsum INT
);

CREATE TABLE chat_links
(
    id      BIGSERIAL PRIMARY KEY,
    chat_id BIGINT,
    link_id BIGINT
);


ALTER TABLE chat_links
    ADD CONSTRAINT fk_chat_links_to_links
        FOREIGN KEY (link_id)
            REFERENCES links (id);

ALTER TABLE chat_links
    ADD CONSTRAINT fk_chat_links_to_chats
        FOREIGN KEY (chat_id)
            REFERENCES chats (id);
