package com.fred.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther fred
 * @create 2021-02-19 9:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    //userID指发表评论的用户的userID
    private Long commentId;
    private Long userID;
    private Long articleID;
    private String comment;
    private String userNickName;
    private String title;

}
