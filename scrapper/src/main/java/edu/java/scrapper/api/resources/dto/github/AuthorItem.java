package edu.java.scrapper.api.resources.dto.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthorItem {

    @JsonProperty("name")
    private String name;

}
