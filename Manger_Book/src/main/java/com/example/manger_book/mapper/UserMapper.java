package com.example.manger_book.mapper;

import com.example.manger_book.pojo.Users;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    @Select("SELECT * FROM users WHERE username = #{username} AND password = #{password}")
    Users getUserByNameAndPassword(@Param("username") String username, @Param("password") String password);

    @Insert("INSERT INTO users (username, password, role) VALUES (#{username}, #{password}, #{role})")
    void insertUser(Users user);

    // 查询所有用户
    @Select("SELECT * FROM users")
    List<Users> getAllUsers();

    // 根据 ID 查询用户
    @Select("SELECT * FROM users WHERE id = #{id}")
    Users getUserById(Integer id);

    // 更新用户
    @Update("UPDATE users SET username = #{username}, password = #{password}, role = #{role} WHERE id = #{id}")
    void updateUser(Users user);

    // 删除用户
    @Delete("DELETE FROM users WHERE id = #{id}")
    void deleteUser(Integer id);

    // 检查用户名是否存在
    @Select("SELECT COUNT(*) FROM users WHERE username = #{username}")
    int countByUsername(String username);
}
