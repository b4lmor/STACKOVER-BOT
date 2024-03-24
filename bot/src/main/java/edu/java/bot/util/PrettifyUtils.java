package edu.java.bot.util;

import edu.java.bot.api.scrapper.dto.request.UpdateDto;
import edu.java.bot.api.scrapper.dto.response.LinkViewDto;
import edu.java.bot.entity.TrackingResource;
import java.util.List;
import lombok.experimental.UtilityClass;
import static edu.java.bot.api.telegram.Commands.TRACK_COMMAND;

@UtilityClass
public class PrettifyUtils {

    public String prettifyLinks(List<LinkViewDto> links) {

        if (links.isEmpty()) {
            return "List of tracked resources is empty! Send " + TRACK_COMMAND + " to add one.";
        }

        StringBuilder prettyListBuilder = new StringBuilder();

        prettyListBuilder.append("List of tracked resources:\n\n");

        for (int linkNumber = 0; linkNumber < links.size(); linkNumber++) {

            var link = links.get(linkNumber);

            prettyListBuilder.append(linkNumber + 1)
                .append(". ")
                .append(convertToHyperlink(link.getValue(), link.getShortName()))
                .append("\n• Resource: [")
                .append(TrackingResource.parseResource(link.getValue()).getBaseUrl())
                .append("]\n\n");
        }

        return prettyListBuilder.toString();
    }

    public String prettifyUpdate(UpdateDto updateDto) {
        StringBuilder prettyListBuilder = new StringBuilder();

        prettyListBuilder
            .append("[")
            .append(convertToHyperlink(updateDto.getBody().link(), updateDto.getBody().name()))
            .append("]")
            .append("\n• ")
            .append(updateDto.getBody().info());

        return prettyListBuilder.toString();
    }

    public String convertToHyperlink(String link, String name) {
        return String.format("<a href=\"%s\">%s</a>", link, name);
    }

}
