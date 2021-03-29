package com.fred.entities.mapper;

import cn.hutool.core.bean.BeanUtil;
import com.fred.entities.ArticleDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * @auther fred
 * @create 2021-03-29 9:19
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailES {

    private Long articleId;
    private Long authorId;
    private String title;//50
    private Long type;
    private String nickname;//t_user
    private String articleContent;//html
    private Long readNum;
    private Long likeNum;//t_like
    private Long markNum;//t_mark

    private String sugTitle;

    public ArticleDetailES(ArticleDetail articleDetail) {
        BeanUtils.copyProperties(articleDetail,this);
        this.sugTitle = articleDetail.getTitle();
    }
}
