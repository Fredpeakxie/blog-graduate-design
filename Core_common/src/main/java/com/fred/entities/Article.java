package com.fred.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther fred
 * @create 2021-02-19 8:59
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    private Long articleID;
    private Long userID;
    private Long type;
    private String title;//50
    private Long readNum;
    private String articleContent;//longtext

    //非表中字段需要跨表查询
    private Long likeNum;//t_like
    private Long markNum;//t_mark



}
