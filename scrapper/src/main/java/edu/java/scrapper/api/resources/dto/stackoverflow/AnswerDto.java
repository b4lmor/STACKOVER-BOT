package edu.java.scrapper.api.resources.dto.stackoverflow;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnswerDto {

    String body;

    long userId;

}
