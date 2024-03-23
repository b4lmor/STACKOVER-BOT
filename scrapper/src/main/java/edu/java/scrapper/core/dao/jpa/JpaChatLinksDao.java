package edu.java.scrapper.core.dao.jpa;

import edu.java.scrapper.core.dao.jpa.entity.JpaChatLinks;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChatLinksDao extends JpaRepository<JpaChatLinks, Long> {

    Optional<JpaChatLinks>  findByChatIdAndShortName(Long chatId, String shortName);

    Optional<JpaChatLinks> findByChatIdAndLinkId(Long chatId, Long linkId);

}
