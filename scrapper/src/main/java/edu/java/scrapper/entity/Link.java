package edu.java.scrapper.entity;

import java.util.Date;
import lombok.Data;

@Data
public class Link {

    Long id;

    String lvalue;

    Integer hashsum;

    Date lastUpdateAt;

}
