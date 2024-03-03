package edu.java.scrapper.api.resources.dto.stackoverflow;

import java.util.List;
import lombok.Data;

@Data
public class StackOverflowAnswersResponse {

    List<AnswerItem> items;

}
