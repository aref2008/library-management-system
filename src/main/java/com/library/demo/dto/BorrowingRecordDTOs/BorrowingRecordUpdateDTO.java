package com.library.demo.dto.BorrowingRecordDTOs;

import java.time.LocalDate;

public class BorrowingRecordUpdateDTO {
    private LocalDate returnDate;

    // Getters and Setters
    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
