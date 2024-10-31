package com.library.demo.dto.BorrowingRecordDTOs;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class BorrowingRecordCreationDTO {
    @NotNull(message = "Book ID is required")
    private Long bookId;

    @NotNull(message = "Patron ID is required")
    private Long patronId;

    @NotNull(message = "Borrow date is required")
    private LocalDate borrowDate;

    private LocalDate returnDate;

    // Getters and Setters
    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getPatronId() {
        return patronId;
    }

    public void setPatronId(Long patronId) {
        this.patronId = patronId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
