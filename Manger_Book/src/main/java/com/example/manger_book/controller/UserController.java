package com.example.manger_book.controller;

import com.example.manger_book.mapper.UserMapper;
import com.example.manger_book.pojo.Users;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional; // 导入事务支持

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserMapper userMapper;

    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @PostMapping("/users")
    @Transactional // 确保操作的原子性
    public ResponseEntity<String> createUser(@RequestBody Users user) {
        try {
            userMapper.insertUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("用户创建成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("用户创建失败: " + e.getMessage());
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = userMapper.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Integer id) {
        Users user = userMapper.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/users/{id}")
    @Transactional
    public ResponseEntity<String> updateUser(@PathVariable Integer id, @RequestBody Users user) {
        Users dbUser = userMapper.getUserById(id);
        if (dbUser != null) {
            user.setId(id); // 确保 ID 不变
            userMapper.updateUser(user);
            return ResponseEntity.ok("用户更新成功");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("用户未找到");
        }
    }

    @DeleteMapping("/users/{id}")
    @Transactional
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        Users dbUser = userMapper.getUserById(id);
        if (dbUser != null) {
            userMapper.deleteUser(id);
            return ResponseEntity.ok("用户删除成功");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("用户未找到");
        }
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Users user) {
        Map<String, String> response = new HashMap<>();
        try {
            int count = userMapper.countByUsername(user.getUsername());
            if (count > 0) {
                response.put("message", "用户名已存在");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            userMapper.insertUser(user);
            response.put("message", "注册成功");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("message", "注册失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
