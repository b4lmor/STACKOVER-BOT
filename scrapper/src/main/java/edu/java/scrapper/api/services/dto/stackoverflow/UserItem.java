package edu.java.scrapper.api.services.dto.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserItem {

    @JsonProperty("display_name")
    String name;

}
