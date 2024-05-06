package edu.java.bot.api.scrapper.dto.request;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Value
@Jacksonized
public class UntrackLinkDto {

    String shortName;

    long chatId;

}
