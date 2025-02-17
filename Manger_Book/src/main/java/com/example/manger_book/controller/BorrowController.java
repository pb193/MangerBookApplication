package com.example.manger_book.controller;

import com.example.manger_book.mapper.BorrowRecordMapper;
import com.example.manger_book.pojo.BorrowRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/borrow")
public class BorrowController {

    @Autowired
    private BorrowRecordMapper borrowRecordMapper;

    // 创建借阅记录
    @PostMapping
    public ResponseEntity<String> createBorrowRecord(@RequestBody BorrowRecord borrowRecord) {
        try {
            LocalDate now = LocalDate.now(); // 当前日期
            borrowRecord.setCreatedAt(now); // 设置创建时间
            borrowRecord.setBorrowDate(now); // 设置借阅日期
            borrowRecord.setStatus("borrowed"); // 设置借阅状态
            borrowRecord.setRequestStatus("approved"); // 设置请求状态

            borrowRecordMapper.insertBorrowRecord(borrowRecord);
            return ResponseEntity.status(HttpStatus.CREATED).body("借阅记录创建成功");
        } catch (Exception e) {
            e.printStackTrace(); // 打印详细错误信息
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("借阅记录创建失败: " + e.getMessage());
        }
    }




    // 获取所有借阅记录
    @GetMapping
    public ResponseEntity<List<BorrowRecord>> getAllBorrowRecords() {
        List<BorrowRecord> records = borrowRecordMapper.getAllBorrowRecords();
        System.out.println(records); // 打印记录以进行调试
        return ResponseEntity.ok(records);
    }

    // 根据 ID 获取借阅记录
    @GetMapping("/{id}")
    public ResponseEntity<BorrowRecord> getBorrowRecordById(@PathVariable Integer id) {
        BorrowRecord record = borrowRecordMapper.getBorrowRecordById(id);
        return record != null ? ResponseEntity.ok(record) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // 更新借阅记录
    @PutMapping("/{id}")
    public ResponseEntity<String> updateBorrowRecord(@PathVariable Integer id, @RequestBody BorrowRecord borrowRecord) {
        BorrowRecord existingRecord = borrowRecordMapper.getBorrowRecordById(id);
        if (existingRecord == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("借阅记录未找到");
        }
        borrowRecord.setId(id);
        borrowRecordMapper.updateBorrowRecord(borrowRecord);
        return ResponseEntity.ok("借阅记录更新成功");
    }

    // 删除借阅记录
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBorrowRecord(@PathVariable Integer id) {
        BorrowRecord existingRecord = borrowRecordMapper.getBorrowRecordById(id);
        if (existingRecord == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("借阅记录未找到");
        }
        borrowRecordMapper.deleteBorrowRecord(id);
        return ResponseEntity.ok("借阅记录删除成功");
    }


}

