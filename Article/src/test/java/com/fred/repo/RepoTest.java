package com.fred.repo;

import com.fred.entities.ArticleDetail;
import com.fred.repository.ArticleESRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @auther fred
 * @create 2021-03-29 18:50
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RepoTest {

    @Resource
    private ArticleESRepo articleESRepo;

    @Test
    public void test() throws IOException {
        List<ArticleDetail> articleDetails = articleESRepo.searchArticle(0, 5, "spring");
        articleDetails.forEach(System.out::println);
    }
}
