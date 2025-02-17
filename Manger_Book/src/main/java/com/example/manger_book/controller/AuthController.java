package com.example.manger_book.controller;

import com.example.manger_book.mapper.UserMapper;
import com.example.manger_book.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users users) {
        // 使用用户名和密码查询数据库中的用户
        Users dbUser = userMapper.getUserByNameAndPassword(users.getUsername(), users.getPassword());

        // 如果用户存在，判断角色并返回不同的响应
        if (dbUser != null) {
            Map<String, String> response = new HashMap<>();
            response.put("role", dbUser.getRole());

            return ResponseEntity.ok(response);
        } else {
            // 如果用户不存在或密码错误，返回 401 Unauthorized
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用户名或密码错误");
        }
    }
}
