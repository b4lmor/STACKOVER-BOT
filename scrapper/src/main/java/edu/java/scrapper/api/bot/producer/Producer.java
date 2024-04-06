package edu.java.scrapper.api.bot.producer;

import edu.java.scrapper.api.bot.dto.response.UpdateDto;

public interface Producer {

    void sendUpdate(UpdateDto updates);

}
