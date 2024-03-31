package edu.java.scrapper.entity;

import java.time.LocalDate;
import lombok.Data;

@Data
public class Link {

    Long id;

    String lvalue;

    Integer hashsum;

    LocalDate lastUpdateAt;

}
