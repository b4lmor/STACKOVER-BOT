package edu.java.scrapper.api.bot.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Value
@Jacksonized
public class UpdateDto {

    List<UpdateBody> body;

    long chatId;

    public record UpdateBody(

        String link,

        String info

    ) {}

}
