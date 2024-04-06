package edu.java.bot.core.validation;

import edu.java.bot.api.scrapper.dto.request.UpdateDto;
import edu.java.bot.entity.TrackingResource;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Validator {

    private static final int LINK_NAME_MAX_SIZE = 40;

    public static final String LINK_PATTERN = "^(http://|https://)?"
        + "("
        + Arrays.stream(TrackingResource.values())
            .map(TrackingResource::getBaseUrl)
            .collect(Collectors.joining("|"))
        + ").*";

    public boolean isValidLink(String link) {
        return link.matches(LINK_PATTERN);
    }

    public boolean isValidLinkName(String linkName) {
        return !linkName.isBlank() && linkName.length() < LINK_NAME_MAX_SIZE;
    }

    public boolean isValidUpdateDto(UpdateDto updateDto) {
        return updateDto.getName() != null
            && updateDto.getLink() != null
            && updateDto.getInfo() != null;
    }

}
