package edu.java.scrapper.api.resources.dto.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OwnerItem {

    @JsonProperty("user_id")
    long userId;

}
