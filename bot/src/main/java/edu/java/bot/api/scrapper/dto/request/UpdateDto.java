package edu.java.bot.api.scrapper.dto.request;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateDto implements Serializable {

    private long chatId;

    private String link;

    private String name;

    private String info;

}
