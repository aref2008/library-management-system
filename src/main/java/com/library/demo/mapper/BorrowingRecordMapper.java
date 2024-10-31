package com.library.demo.mapper;

import com.library.demo.dto.BorrowingRecordDTOs.BorrowingRecordCreationDTO;
import com.library.demo.dto.BorrowingRecordDTOs.BorrowingRecordResponseDTO;
import com.library.demo.model.BorrowingRecord;
import com.library.demo.model.Book;
import com.library.demo.model.Patron;

public class BorrowingRecordMapper {

    public static BorrowingRecord toEntity(BorrowingRecordCreationDTO dto, Book book, Patron patron) {
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(dto.getBorrowDate());
        borrowingRecord.setReturnDate(dto.getReturnDate());
        return borrowingRecord;
    }

    public static BorrowingRecordResponseDTO toResponseDTO(BorrowingRecord borrowingRecord) {
        BorrowingRecordResponseDTO responseDTO = new BorrowingRecordResponseDTO();
        responseDTO.setId(borrowingRecord.getId());
        responseDTO.setBookId(borrowingRecord.getBook().getId());
        responseDTO.setPatronId(borrowingRecord.getPatron().getId());
        responseDTO.setBorrowDate(borrowingRecord.getBorrowDate());
        responseDTO.setReturnDate(borrowingRecord.getReturnDate());
        return responseDTO;
    }
}
