package edu.java.scrapper.api.services.dto.stackoverflow;

import java.util.List;
import lombok.Data;

@Data
public class StackOverflowUserResponse {

    List<UserItem> items;

}
