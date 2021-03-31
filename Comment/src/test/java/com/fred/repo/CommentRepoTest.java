package com.fred.repo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @auther fred
 * @create 2021-03-30 14:35
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CommentRepoTest {

    @Resource
    private CommentRepo commentRepo;

    @Test
    public void getCommentByArticleId(){
        commentRepo.getCommentByArticleId(1L).forEach(System.out::println);
    }

}
