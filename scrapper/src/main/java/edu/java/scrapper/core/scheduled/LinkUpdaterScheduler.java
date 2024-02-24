package edu.java.scrapper.core.scheduled;

import edu.java.scrapper.core.sources.GithubService;
import edu.java.scrapper.core.sources.StackOverflowService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class LinkUpdaterScheduler {

    private final GithubService githubService;
    private final StackOverflowService stackOverflowService;

    @Autowired
    public LinkUpdaterScheduler(GithubService githubService, StackOverflowService stackOverflowService) {
        this.githubService = githubService;
        this.stackOverflowService = stackOverflowService;
    }

    @Scheduled(fixedDelayString = "#{@interval}")
    public void update() {

        log.info("getting all tracked links ...");

        String repo = "Tinkoff-Backend";
        String owner = "b4lmor";
        long questionId = 66675088;

        log.info("getting all updates ...");

        var newCommits = githubService.getUpdates(repo, owner);
        var newAnswers = stackOverflowService.getUpdates(questionId);

        log.info("saving all updates ...");

        log.info("Done");
    }

}
