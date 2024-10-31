package com.library.demo.service;

import com.library.demo.dto.BorrowingRecordDTOs.BorrowingRecordCreationDTO;
import com.library.demo.dto.BorrowingRecordDTOs.BorrowingRecordResponseDTO;
import com.library.demo.dto.BorrowingRecordDTOs.BorrowingRecordUpdateDTO;
import com.library.demo.mapper.BorrowingRecordMapper;
import com.library.demo.model.BorrowingRecord;
import com.library.demo.model.Book;
import com.library.demo.model.Patron;
import com.library.demo.repository.BorrowingRecordRepository;
import com.library.demo.repository.BookRepository;
import com.library.demo.repository.PatronRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BorrowingRecordService {
    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;

    public BorrowingRecordService(BorrowingRecordRepository borrowingRecordRepository, BookRepository bookRepository, PatronRepository patronRepository) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
    }

    public BorrowingRecordResponseDTO createBorrowingRecord(BorrowingRecordCreationDTO dto) {
        Book book = bookRepository.findById(dto.getBookId()).orElseThrow(() -> new RuntimeException("Book not found"));
        Patron patron = patronRepository.findById(dto.getPatronId()).orElseThrow(() -> new RuntimeException("Patron not found"));
        
        BorrowingRecord borrowingRecord = BorrowingRecordMapper.toEntity(dto, book, patron);
        BorrowingRecord savedRecord = borrowingRecordRepository.save(borrowingRecord);
        
        return BorrowingRecordMapper.toResponseDTO(savedRecord);
    }

    public List<BorrowingRecordResponseDTO> getAllBorrowingRecords() {
        return borrowingRecordRepository.findAll()
                .stream()
                .map(BorrowingRecordMapper::toResponseDTO)
                .toList();
    }

    public Optional<BorrowingRecordResponseDTO> getBorrowingRecordById(Long id) {
        return borrowingRecordRepository.findById(id)
                .map(BorrowingRecordMapper::toResponseDTO);
    }

    public Optional<BorrowingRecordResponseDTO> updateBorrowingRecord(Long id, BorrowingRecordUpdateDTO dto) {
        return borrowingRecordRepository.findById(id)
                .map(borrowingRecord -> {
                    if (dto.getReturnDate() != null) {
                        borrowingRecord.setReturnDate(dto.getReturnDate());
                    }
                    BorrowingRecord updatedRecord = borrowingRecordRepository.save(borrowingRecord);
                    return BorrowingRecordMapper.toResponseDTO(updatedRecord);
                });
    }

    public boolean deleteBorrowingRecord(Long id) {
        if (borrowingRecordRepository.existsById(id)) {
            borrowingRecordRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
