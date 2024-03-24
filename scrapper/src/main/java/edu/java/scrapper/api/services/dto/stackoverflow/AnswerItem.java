package edu.java.scrapper.api.services.dto.stackoverflow;

import lombok.Data;

@Data
public class AnswerItem {

    OwnerItem owner;

    String body;

}
