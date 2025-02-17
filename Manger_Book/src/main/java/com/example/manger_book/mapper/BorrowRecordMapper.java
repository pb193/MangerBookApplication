package com.example.manger_book.mapper;

import com.example.manger_book.pojo.BorrowRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 借阅记录 Mapper 接口
 */
public interface BorrowRecordMapper {

    /**
     * 插入新的借阅记录
     * @param borrowRecord 借阅记录对象
     */
    @Insert("INSERT INTO borrow_records (user_id, book_id, borrow_date, return_date, status, request_status, created_at) " +
            "VALUES (#{userId}, #{bookId}, #{borrowDate}, #{returnDate}, #{status}, #{requestStatus}, #{createdAt})")
    void insertBorrowRecord(BorrowRecord borrowRecord);

    /**
     * 获取所有借阅记录
     * @return 借阅记录列表
     */
    @Select("SELECT * FROM borrow_records")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "bookId", column = "book_id"),
            @Result(property = "borrowDate", column = "borrow_date"),
            @Result(property = "returnDate", column = "return_date"),
            @Result(property = "status", column = "status"),
            @Result(property = "requestStatus", column = "request_status"),
            @Result(property = "createdAt", column = "created_at")
    })
    List<BorrowRecord> getAllBorrowRecords();

    /**
     * 根据 ID 获取特定的借阅记录
     * @param id 借阅记录 ID
     * @return 借阅记录对象
     */
    @Select("SELECT * FROM borrow_records WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "bookId", column = "book_id"),
            @Result(property = "borrowDate", column = "borrow_date"),
            @Result(property = "returnDate", column = "return_date"),
            @Result(property = "status", column = "status"),
            @Result(property = "requestStatus", column = "request_status"),
            @Result(property = "createdAt", column = "created_at")
    })
    BorrowRecord getBorrowRecordById(Integer id);

    /**
     * 更新现有的借阅记录
     * @param borrowRecord 借阅记录对象
     */
    @Update("UPDATE borrow_records SET user_id = #{userId}, book_id = #{bookId}, " +
            "borrow_date = #{borrowDate}, return_date = #{returnDate}, status = #{status} " +
            "WHERE id = #{id}")
    void updateBorrowRecord(BorrowRecord borrowRecord);

    /**
     * 根据 ID 删除借阅记录
     * @param id 借阅记录 ID
     */
    @Delete("DELETE FROM borrow_records WHERE id = #{id}")
    void deleteBorrowRecord(Integer id);
}
