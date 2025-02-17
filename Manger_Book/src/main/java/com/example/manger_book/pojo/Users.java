package com.example.manger_book.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Users {


    private Integer id;

    private String username;

    private String password;

    private String role;

    private LocalDateTime createdAt;

}
