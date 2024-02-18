package edu.java.bot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Link {

    private TrackingResource resource;

    private String value;

    private String name;

    public Link(String link, String name) {
        this(TrackingResource.parseResource(link), link, name);
    }

}
