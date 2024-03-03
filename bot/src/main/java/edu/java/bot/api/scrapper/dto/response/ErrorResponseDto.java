package edu.java.bot.api.scrapper.dto.response;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Value
@Jacksonized
public class ErrorResponseDto {

    String type = "error";

    int code;

    String message;

}

