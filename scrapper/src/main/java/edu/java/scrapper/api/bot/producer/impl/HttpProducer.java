package edu.java.scrapper.api.bot.producer.impl;

import edu.java.scrapper.api.bot.client.BotClient;
import edu.java.scrapper.api.bot.dto.response.UpdateDto;
import edu.java.scrapper.api.bot.producer.Producer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HttpProducer implements Producer {

    private final BotClient botClient;

    @Override
    public void sendUpdate(UpdateDto update) {
        botClient.sendUpdate(update);
    }
}
