package edu.java.scrapper.entity;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import static edu.java.scrapper.core.validation.LinkValidator.GITHUB_PATTERN;
import static edu.java.scrapper.core.validation.LinkValidator.SOF_PATTERN;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum TrackingResource {

    GITHUB("github.com", GITHUB_PATTERN),

    STACKOVERFLOW("stackoverflow.com", SOF_PATTERN);

    private static final String HTTP_PATH = "http://";

    private static final String HTTPS_PATH = "https://";

    private final String baseUrl;

    private final String pattern;

    public static TrackingResource parseResource(String link) {

        String unboundLink;

        if (link.startsWith(HTTP_PATH)) {
            unboundLink = link.substring(HTTP_PATH.length());

        } else if (link.startsWith(HTTPS_PATH)) {
            unboundLink = link.substring(HTTPS_PATH.length());

        } else {
            unboundLink = link;
        }

        return Arrays.stream(TrackingResource.values())
            .filter(r -> unboundLink.startsWith(r.baseUrl))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Can't parse resource type!"));
    }

}
