package edu.java.scrapper.core.tracked;

public record RawUpdate(

    int newHashsum,

    String message

) {

    public static final int MAX_MESSAGE_LENGTH = 50;
    public static final int SOF_SKIP_MESSAGE_LENGTH = 3; // To skip '<p>'

    public static final String DOTS = "...";

}
