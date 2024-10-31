package com.library.demo.repository;

import com.library.demo.model.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
    List<BorrowingRecord> findByPatronId(Long patronId);
    List<BorrowingRecord> findByBookId(Long bookId);
}
