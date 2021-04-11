package com.fred.task;

import com.fred.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @auther fred
 * @create 2021-04-11 15:56
 */
@Slf4j
@Configuration
@EnableScheduling
public class UpdateELKInfoTask {

    @Resource
    ArticleService articleService;


    @Scheduled(cron = "0 */1 * * * ?")
    private void updateELK() {
        log.info("execute the Task(update ELK)" + LocalDateTime.now());
        articleService.saveArticlesToELK(100);
    }

}
