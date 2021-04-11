package com.fred.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther fred
 * @create 2021-03-13 20:45
 */

public enum RetCode {
    OK(200),
    WRONG_PASSWORD(400),
    REPETITIVE_USERNAME(410),
    REPETITIVE_EMAIL(411),
    REPETITIVE_USERNAME_EMAIL(412),
    ARTICLE_PIC_SAVE_FAIL(501),
    ARTICLE_HTML_SAVE_FAIL(502),
    USER_LIKE_ADD_FAIL(511),
    USER_MARK_ADD_FAIL(512),
    USER_LIKE_REMOVE_FAIL(513),
    USER_MARK_REMOVE_FAIL(514),
    USER_PORTRAIT_SAVE_FAIL(521),
    SEARCH_ARTICLE_FAIL(531),
    SUGGEST_TITLE_FAIL(532),
    COMMENT_PUBLISH_FAIL(541),
    SAVE_ARTICLES_TO_ELK_FAIL(551)
    ;

    private final Integer code;

    RetCode(Integer code) {
        this.code = code;
    }
}
