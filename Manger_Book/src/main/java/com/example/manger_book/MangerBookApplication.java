package com.example.manger_book;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.manger_book.mapper")
@SpringBootApplication
public class MangerBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(MangerBookApplication.class, args);
    }

}
