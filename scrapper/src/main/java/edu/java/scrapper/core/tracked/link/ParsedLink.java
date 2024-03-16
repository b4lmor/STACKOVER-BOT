package edu.java.scrapper.core.tracked.link;

import edu.java.scrapper.entity.TrackingResource;
import lombok.Getter;

@Getter
public abstract class ParsedLink {

    private final TrackingResource trackingResource;

    private final String value;

    protected ParsedLink(TrackingResource trackingResource, String value) {
        this.trackingResource = trackingResource;
        this.value = value;
    }

}
