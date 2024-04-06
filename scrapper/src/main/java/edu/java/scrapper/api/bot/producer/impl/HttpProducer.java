package edu.java.scrapper.api.bot.producer.impl;

import edu.java.scrapper.api.bot.client.BotClient;
import edu.java.scrapper.api.bot.dto.response.UpdateDto;
import edu.java.scrapper.api.bot.producer.Producer;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
public class HttpProducer implements Producer {

    private final BotClient botClient;

    @Override
    public void sendUpdates(List<UpdateDto> updates) {
        botClient.sendUpdates(updates);
    }
}
