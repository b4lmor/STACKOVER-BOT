package edu.java.bot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Link {

    private TrackingResource resource;

    private String value;

    public Link(String link) {
        this(TrackingResource.parseResource(link), link);
    }

}
