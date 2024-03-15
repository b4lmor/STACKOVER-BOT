package edu.java.scrapper.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Chat {

    long id;

    long chatId;

    Date createdAt;

    String createdBy;

}
