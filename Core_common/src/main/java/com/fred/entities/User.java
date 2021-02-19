package com.fred.entities;

import lombok.*;

/**
 * @auther fred
 * @create 2021-02-19 8:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long UserID;
    private String username;//20
    private String password;//20
    private String email;//20
    private String nickname;//20
    private String introduction;//100

}
