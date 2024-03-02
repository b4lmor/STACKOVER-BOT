package edu.java.bot.util;

import edu.java.bot.entity.Link;
import java.util.List;
import lombok.experimental.UtilityClass;
import static edu.java.bot.api.Commands.TRACK_COMMAND;

@UtilityClass
public class PrettifyUtils {

    public String prettifyLinks(List<Link> links) {

        if (links.isEmpty()) {
            return "List of tracked resources is empty! Send " + TRACK_COMMAND + " to add one.";
        }

        StringBuilder prettyListBuilder = new StringBuilder();

        prettyListBuilder.append("List of tracked resources:\n\n");

        for (int linkNumber = 0; linkNumber < links.size(); linkNumber++) {

            var link = links.get(linkNumber);

            prettyListBuilder.append(linkNumber + 1)
                .append(". ")
                .append(convertToHyperlink(link.getValue(), link.getName()))
                .append("\n• Resource: [")
                .append(link.getResource().getBaseUrl())
                .append("]\n\n");
        }

        return prettyListBuilder.toString();
    }

    public String convertToHyperlink(String link, String name) {
        return String.format("<a href=\"%s\">%s</a>", link, name);
    }

}
