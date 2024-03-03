package edu.java.scrapper.api.resources.dto.stackoverflow;

import lombok.Data;

@Data
public class AnswerItem {

    OwnerItem owner;

    String body;

}
