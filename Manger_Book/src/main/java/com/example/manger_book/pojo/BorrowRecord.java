package com.example.manger_book.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
/*id: 借阅记录的唯一标识符。
userId: 借书用户的 ID。
bookId: 被借阅书籍的 ID。
borrowDate: 借书日期。
returnDate: 归还日期，可能为 null。
status: 表示借阅状态，值可以是 'borrowed' 或 'returned'。
createdAt: 记录创建时间，通常为当前时间。*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BorrowRecord {
    private Integer id;
    private Integer userId;
    private Integer bookId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private String status; // borrowed or returned
    private String requestStatus; // pending, approved, or rejected
    private LocalDate createdAt;
}
