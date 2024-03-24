package edu.java.scrapper.api.bot.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import static edu.java.scrapper.core.validation.LinkValidator.LINK_PATTERN;

@Builder
@Value
@Jacksonized
public class LinkDto {

    @Pattern(regexp = LINK_PATTERN)
    String value;

    String shortName;

    long chatId;

}
