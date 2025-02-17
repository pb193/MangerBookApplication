package com.example.manger_book.controller;

import com.example.manger_book.mapper.BookMapper;
import com.example.manger_book.pojo.Book;
import com.example.manger_book.pojo.BorrowRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/*创建图书 (POST /books)：

接收一个 Book 对象并调用 insertBook 方法来保存新图书。
获取所有图书 (GET /books)：

调用 getAllBooks 方法返回所有图书的列表。
根据 ID 获取图书 (GET /books/{id})：

使用 getBookById 方法查找特定 ID 的图书，并根据结果返回相应的 HTTP 状态码。
更新图书 (PUT /books/{id})：

将传入的 Book 对象的 ID 设置为路径参数的 ID，并调用 updateBook 方法更新图书。
删除图书 (DELETE /books/{id})：

调用 deleteBook 方法并检查删除的行数，以确定图书是否存在。
获取图书数量 (GET /books/count)：

返回图书总数。
借阅图书 (POST /books/borrow/{bookId})：

检查图书是否可借，如果可借，则创建借阅记录并设置状态为“借阅中”。
批准借阅请求 (POST /books/approve/{id})：

通过调用 approveBorrowRequest 方法处理借阅请求，并根据操作结果返回成功或失败的响应。*/

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookMapper bookMapper;

    // Create book
    @PostMapping
    public ResponseEntity<String> createBook(@RequestBody Book book) {
        bookMapper.insertBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body("图书创建成功");
    }

    // Get all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookMapper.getAllBooks();
        return ResponseEntity.ok(books);
    }

    // Get book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer id) {
        Book book = bookMapper.getBookById(id);
        return book != null ? ResponseEntity.ok(book) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    //库存
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {
        Book book = bookMapper.getBookByIsbn(isbn);
        if (book != null) {
            int quantity = bookMapper.getBookCountByIsbn(isbn);
            book.setQuantity(quantity);  // 设置库存数量
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Update book
    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(@PathVariable Integer id, @RequestBody Book book) {
        book.setId(id);
        bookMapper.updateBook(book);
        return ResponseEntity.ok("图书更新成功");
    }

    // Delete book
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Integer id) {
        int deletedRows = bookMapper.deleteBook(id);
        if (deletedRows > 0) {
            return ResponseEntity.ok("图书删除成功");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("图书未找到");
        }
    }

    // Get book count
    @GetMapping("/count")
    public ResponseEntity<Integer> getBookCount() {
        int count = bookMapper.getBookCount();
        return ResponseEntity.ok(count);
    }

    // Borrow book
    @PostMapping("/borrow/{bookId}")
    public ResponseEntity<String> borrowBook(@PathVariable Integer bookId, @RequestBody BorrowRecord borrowRecord) {
        if (bookMapper.isBookAvailable(bookId)) {
            borrowRecord.setBookId(bookId);
            borrowRecord.setBorrowDate(LocalDate.now());
            borrowRecord.setStatus("借阅中");
            bookMapper.insertBorrowRecord(borrowRecord);
            return ResponseEntity.ok("借阅成功");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("该书籍不可借阅");
        }
    }

    // Approve borrow request
    @PostMapping("/approve/{id}")
    public ResponseEntity<?> approveRequest(@PathVariable Long id) {
        try {
            bookMapper.approveBorrowRequest(id);
            return ResponseEntity.ok(Map.of("success", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "批准请求失败"));
        }
    }

}
