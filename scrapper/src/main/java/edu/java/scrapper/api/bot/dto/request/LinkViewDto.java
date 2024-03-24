package edu.java.scrapper.api.bot.dto.request;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Value
@Jacksonized
public class LinkViewDto {

    String value;

    String shortName;

}
