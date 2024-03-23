package edu.java.scrapper.core.dao.jpa.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "links")
@Data
public class JpaLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "last_update_at")
    private Date lastUpdateAt;

    @Column(name = "lvalue")
    private String lvalue;

    @Column(name = "hashsum")
    private Integer hashsum;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "links", cascade = CascadeType.ALL)
    private List<JpaChat> chats = new ArrayList<>();

}
