package edu.java.scrapper.api.bot.producer;

import edu.java.scrapper.api.bot.dto.response.UpdateDto;
import java.util.List;

public interface Producer {

    void sendUpdates(List<UpdateDto> updates);

}
