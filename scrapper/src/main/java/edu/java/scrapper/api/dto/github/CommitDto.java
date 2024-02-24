package edu.java.scrapper.api.dto.github;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommitDto {

    private String author;

    private String message;

}
