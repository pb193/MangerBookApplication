package com.example.manger_book.mapper;

import com.example.manger_book.pojo.Book;
import com.example.manger_book.pojo.BorrowRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

/*插入图书 (insertBook)：


获取所有图书 (getAllBooks)：

使用 @Select 注解，返回所有图书的列表。
根据 ID 获取图书 (getBookById)：

使用 @Select 注解查找特定 ID 的图书。
更新图书 (updateBook)：

使用 @Update 注解更新图书信息，确保 ID 保持不变。
删除图书 (deleteBook)：

使用 @Delete 注解删除指定 ID 的图书，返回删除的行数。
获取图书数量 (getBookCount)：

使用 @Select 注解返回图书总数。
插入借阅记录 (insertBorrowRecord)：

正确使用 @Insert 注解，将借阅记录插入到数据库中。
检查图书是否可借 (isBookAvailable)：

使用 @Select 注解检查图书是否可借。
批准借阅请求 (approveBorrowRequest)：

使用 @Update 注解将借阅记录的状态更新为“已批准”*/

public interface BookMapper {
/*插入图书 (insertBook)：*/
    @Insert("INSERT INTO books (title, author, genre, published_date, isbn, quantity) VALUES (#{title}, #{author}, #{genre}, #{publishedDate}, #{isbn}, #{quantity})")
    void insertBook(Book book);
    /*获取所有图书 (getAllBooks)：*/
    @Select("SELECT * FROM books")
    List<Book> getAllBooks();

   /* 使用 @Select 注解，返回所有图书的列表。
    根据 ID 获取图书 (getBookById)：*/
    @Select("SELECT * FROM books WHERE id = #{id}")
    Book getBookById(Integer id);


    /*使用 @Update 注解更新图书信息，确保 ID 保持不变。
删除图书 (deleteBook)：*/
    @Update("UPDATE books SET title = #{title}, author = #{author}, genre = #{genre}, published_date = #{publishedDate}, isbn = #{isbn}, quantity = #{quantity} WHERE id = #{id}")
    void updateBook(Book book);

    /*使用 @Delete 注解删除指定 ID 的图书，返回删除的行数。
获取图书数量 (getBookCount)：*/
    @Delete("DELETE FROM books WHERE id = #{id}")
    int deleteBook(Integer id);

    /*使用 @Select 注解返回图书总数。
插入借阅记录 (insertBorrowRecord)：*/
    @Select("SELECT COUNT(*) FROM books")
    int getBookCount();
    //以isbn来查询库存数量
    @Select("SELECT * FROM books WHERE isbn = #{isbn}")
    Book getBookByIsbn(String isbn);

    @Select("SELECT quantity FROM books WHERE isbn = #{isbn}")
    int getBookCountByIsbn(String isbn);

    @Insert("INSERT INTO borrow_records (user_id, book_id, borrow_date, status) VALUES (#{userId}, #{bookId}, #{borrowDate}, #{status})")
    void insertBorrowRecord(BorrowRecord borrowRecord);

   /* 使用 @Select 注解检查图书是否可借。
    批准借阅请求 (approveBorrowRequest)：*/
    @Select("SELECT b.quantity > 0 AND COUNT(br.id) = 0 " +
            "FROM books b " +
            "LEFT JOIN borrow_records br ON b.id = br.book_id AND br.status = 'borrowed' " +
            "WHERE b.id = #{bookId} " +
            "GROUP BY b.id")
    boolean isBookAvailable(Integer bookId);


/*使用 @Update 注解将借阅记录的状态更新为“已批准”*/
    @Update("UPDATE borrow_records SET status = 'approved' WHERE id = #{id}")
    void approveBorrowRequest(Long id);


}
