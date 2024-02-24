package edu.java.scrapper.api.dto.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Owner {
    @JsonProperty("user_id")
    long userId;

}
