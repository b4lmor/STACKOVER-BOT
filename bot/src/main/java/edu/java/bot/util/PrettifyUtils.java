package edu.java.bot.util;

import edu.java.bot.entity.Link;
import lombok.experimental.UtilityClass;
import java.util.List;

@UtilityClass
public class PrettifyUtils {

    public String prettifyLinks(List<Link> links) {
        StringBuilder sb = new StringBuilder();

        sb.append("List of tracked resources:\n\n");

        for (Link link : links) {
            sb.append("• ");
            sb.append(link.getResource());
            sb.append(":\n");
            sb.append(link.getValue());
            sb.append("\n\n");
        }

        return sb.toString();
    }

}
