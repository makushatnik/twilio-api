package com.example.myproj.schedule;

import com.example.myproj.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * ArchiveExpiredPostsTask.
 *
 * @author Evgeny_Ageev
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ArchiveExpiredPostsTask {
    private final PostService service;

    @Value("${app.archive-posts.enabled}")
    private boolean enabled;

    @Scheduled(cron = "${jobs.archive-posts}")
    public void transfer() {
        if (!enabled) return;

        log.info("Start Transferring Posts");
        service.archiveExpired();
        log.info("End Transferring Posts");
    }
}
