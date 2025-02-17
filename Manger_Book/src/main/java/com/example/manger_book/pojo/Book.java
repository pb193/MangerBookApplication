package com.example.manger_book.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

/*id: 图书的唯一标识符。
title: 图书标题。
author: 作者姓名。
genre: 图书类别。
publishedDate: 出版日期。
isbn: 国际标准书号。
quantity: 库存数量。*/

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book {
    private Integer id;
    private String title;
    private String author;
    private String genre;
    private LocalDate publishedDate;
    private String isbn;
    private Integer quantity;

    // Getter 和 Setter 方法（可使用 Lombok 的 @Data 注解）
}
