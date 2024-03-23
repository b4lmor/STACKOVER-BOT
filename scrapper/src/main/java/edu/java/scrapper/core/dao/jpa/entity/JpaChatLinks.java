package edu.java.scrapper.core.dao.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "chat_links")
@Data
public class JpaChatLinks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "link_id")
    private Long linkId;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "short_name")
    private String shortName;

}
