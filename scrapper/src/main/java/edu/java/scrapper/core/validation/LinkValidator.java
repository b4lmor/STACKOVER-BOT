package edu.java.scrapper.core.validation;

import edu.java.scrapper.entity.TrackingResource;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LinkValidator {

    public static final String GITHUB_PATTERN
        = "^.*github.com/(.*)/([^/]*)";

    public static final String SOF_PATTERN
        = "^.*stackoverflow.com/questions/(\\d+).*";

    public static final String LINK_PATTERN = GITHUB_PATTERN + '|' + SOF_PATTERN;

    public static final String LINK_PATTERN_DYNAMIC = Arrays.stream(TrackingResource.values())
        .map(TrackingResource::getPattern)
        .collect(Collectors.joining("|"));

}
