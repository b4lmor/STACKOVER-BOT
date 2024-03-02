package edu.java.scrapper.api.dto.stackoverflow;

import java.util.List;
import lombok.Data;

@Data
public class StackOverflowAnswersResponse {

    List<AnswerItem> items;

}
