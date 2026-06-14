package ge.tsu.ByteSized.scheduler;

import ge.tsu.ByteSized.repository.BlogPostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BlogMaintenanceScheduler {

    private static final Logger log = LoggerFactory.getLogger(BlogMaintenanceScheduler.class);

    private final BlogPostRepository blogPostRepository;

    public BlogMaintenanceScheduler(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void logPostCount() {
        long totalPosts = blogPostRepository.count();
        log.info("Scheduled maintenance check: {} blog post(s) in the database.", totalPosts);
    }
}
