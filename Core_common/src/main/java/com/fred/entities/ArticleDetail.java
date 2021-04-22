package com.fred.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @auther fred
 * @create 2021-03-16 20:18
 * 对应视图 v_articledetail
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetail {
    private Long articleId;
    private Long authorId;
    private String title;//50
    private Long type;
    private String nickname;//t_user
    private String articleContent;//html
    private Long readNum;
    private Long likeNum;//t_like
    private Long markNum;//t_mark
    private String href;//backOffice
}
