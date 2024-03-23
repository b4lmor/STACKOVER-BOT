package edu.java.scrapper.entity;

import java.util.Date;
import lombok.Data;

@Data
public class Chat {

    Long id;

    Long tgChatId;

    Date createdAt;

    Boolean isActive;

}
