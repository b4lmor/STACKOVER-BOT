package edu.java.scrapper.api.services.dto.stackoverflow;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnswerDto {

    String body;

    long userId;

}
