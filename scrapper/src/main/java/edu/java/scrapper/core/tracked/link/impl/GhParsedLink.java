package edu.java.scrapper.core.tracked.link.impl;

import edu.java.scrapper.core.tracked.link.ParsedLink;
import edu.java.scrapper.entity.TrackingResource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;
import static edu.java.scrapper.core.validation.LinkValidator.GITHUB_PATTERN;

@Getter
public class GhParsedLink extends ParsedLink {

    private final String owner;

    private final String repo;

    protected GhParsedLink(TrackingResource trackingResource, String value, String owner, String repo) {
        super(trackingResource, value);
        this.owner = owner;
        this.repo = repo;
    }

    public static GhParsedLink ofLink(String link) {

        Pattern pattern = Pattern.compile(GITHUB_PATTERN);
        Matcher matcher = pattern.matcher(link);

        if (matcher.find()) {
            String owner = matcher.group(1);
            String repository = matcher.group(2);
            return new GhParsedLink(TrackingResource.GITHUB, link, owner, repository);
        } else {
            throw new RuntimeException("Can't parse github link!");
        }
    }

}
