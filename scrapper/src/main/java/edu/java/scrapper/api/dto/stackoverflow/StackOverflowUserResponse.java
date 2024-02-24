package edu.java.scrapper.api.dto.stackoverflow;

import lombok.Data;
import java.util.List;

@Data
public class StackOverflowUserResponse {

    List<UserItem> items;

}
