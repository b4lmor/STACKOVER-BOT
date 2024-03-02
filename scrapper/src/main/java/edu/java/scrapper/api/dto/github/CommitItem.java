package edu.java.scrapper.api.dto.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommitItem {

    @JsonProperty("author")
    AuthorItem authorItem;

    @JsonProperty("message")
    String message;

}
