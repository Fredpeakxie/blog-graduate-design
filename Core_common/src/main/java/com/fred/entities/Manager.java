package com.fred.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther fred
 * @create 2021-04-19 15:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Manager {
    private Long managerId;
    private Long userId;
    private String username;
    private String password;
    private String email;
}
