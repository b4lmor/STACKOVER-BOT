package edu.java.scrapper.core.dao.jpa;

import edu.java.scrapper.core.dao.jpa.entity.JpaChat;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaChatDao extends JpaRepository<JpaChat, Long> {

    Optional<JpaChat> findByTgChatId(Long tgChatId);

    void deleteByTgChatId(Long tgChatId);

}
