package edu.java.scrapper.entity;

import java.util.Date;
import lombok.Data;

@Data
public class Chat {

    long id;

    String createdBy;

    Date createdAt;

}
