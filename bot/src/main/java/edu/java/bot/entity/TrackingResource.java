package edu.java.bot.entity;

public enum TrackingResource {

    GITHUB,

    STACKOVERFLOW;

    public static TrackingResource parseResource(String link) {
        return STACKOVERFLOW; // TODO
    }

}
