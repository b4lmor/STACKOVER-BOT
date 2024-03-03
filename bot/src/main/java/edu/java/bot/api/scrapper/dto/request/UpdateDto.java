package edu.java.bot.api.scrapper.dto.request;

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
