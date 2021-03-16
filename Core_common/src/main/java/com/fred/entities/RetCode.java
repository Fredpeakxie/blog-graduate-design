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
    ;

    private final Integer code;

    RetCode(Integer code) {
        this.code = code;
    }
}