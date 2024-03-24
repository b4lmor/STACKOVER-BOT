package edu.java.bot.api.scrapper.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Value
@Jacksonized
public class IsActiveChatDto {

    @JsonProperty("open")
    boolean isOpen;

}
