package edu.java.scrapper.core.tracked.link.impl;

import edu.java.scrapper.core.tracked.link.ParsedLink;
import edu.java.scrapper.entity.TrackingResource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;
import static edu.java.scrapper.core.validation.LinkValidator.SOF_PATTERN;

@Getter
public class SofParsedLink extends ParsedLink {

    private final long questionId;

    protected SofParsedLink(TrackingResource trackingResource, String value, long questionId) {
        super(trackingResource, value);
        this.questionId = questionId;
    }

    public static SofParsedLink ofLink(String link) {

        Pattern pattern = Pattern.compile(SOF_PATTERN);
        Matcher matcher = pattern.matcher(link);

        if (matcher.find()) {
            long questionId = Long.parseLong(matcher.group(1));
            return new SofParsedLink(TrackingResource.STACKOVERFLOW, link, questionId);
        } else {
            throw new RuntimeException("Can't parse sof link!");
        }
    }

}
