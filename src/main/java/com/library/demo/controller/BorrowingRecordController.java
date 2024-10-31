package com.library.demo.controller;

import com.library.demo.dto.BorrowingRecordDTOs.BorrowingRecordCreationDTO;
import com.library.demo.dto.BorrowingRecordDTOs.BorrowingRecordResponseDTO;
import com.library.demo.dto.BorrowingRecordDTOs.BorrowingRecordUpdateDTO;
import com.library.demo.service.BorrowingRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrowing-records")
public class BorrowingRecordController {
    private final BorrowingRecordService borrowingRecordService;

    public BorrowingRecordController(BorrowingRecordService borrowingRecordService) {
        this.borrowingRecordService = borrowingRecordService;
    }

    /**
     * Creates a new borrowing record.
     *
     * @param dto the borrowing record creation details
     * @return the created borrowing record response DTO
     */
    @Operation(summary = "Create a new borrowing record", description = "Creates a new borrowing record with the provided details.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Borrowing record created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping
    public ResponseEntity<BorrowingRecordResponseDTO> createBorrowingRecord(@RequestBody BorrowingRecordCreationDTO dto) {
        BorrowingRecordResponseDTO createdRecord = borrowingRecordService.createBorrowingRecord(dto);
        return new ResponseEntity<>(createdRecord, HttpStatus.CREATED);
    }

    /**
     * Retrieves all borrowing records.
     *
     * @return a list of all borrowing records
     */
    @Operation(summary = "Get all borrowing records", description = "Retrieves a list of all borrowing records.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of borrowing records retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<BorrowingRecordResponseDTO>> getAllBorrowingRecords() {
        List<BorrowingRecordResponseDTO> records = borrowingRecordService.getAllBorrowingRecords();
        return ResponseEntity.ok(records);
    }

    /**
     * Retrieves a borrowing record by its ID.
     *
     * @param id the ID of the borrowing record to retrieve
     * @return the borrowing record response DTO if found
     */
    @Operation(summary = "Get a borrowing record by ID", description = "Retrieves a specific borrowing record by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Borrowing record found"),
        @ApiResponse(responseCode = "404", description = "Borrowing record not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BorrowingRecordResponseDTO> getBorrowingRecordById(@Parameter(description = "ID of the borrowing record to retrieve") @PathVariable Long id) {
        return borrowingRecordService.getBorrowingRecordById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Updates an existing borrowing record by its ID.
     *
     * @param id  the ID of the borrowing record to update
     * @param dto the borrowing record update details
     * @return the updated borrowing record response DTO if found
     */
    @Operation(summary = "Update a borrowing record", description = "Updates the details of an existing borrowing record by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Borrowing record updated successfully"),
        @ApiResponse(responseCode = "404", description = "Borrowing record not found"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BorrowingRecordResponseDTO> updateBorrowingRecord(
            @Parameter(description = "ID of the borrowing record to update") @PathVariable Long id,
            @RequestBody BorrowingRecordUpdateDTO dto) {
        return borrowingRecordService.updateBorrowingRecord(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Deletes a borrowing record by its ID.
     *
     * @param id the ID of the borrowing record to delete
     * @return no content if the borrowing record was deleted, or not found if it doesn't exist
     */
    @Operation(summary = "Delete a borrowing record", description = "Deletes a specific borrowing record by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Borrowing record deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Borrowing record not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrowingRecord(@Parameter(description = "ID of the borrowing record to delete") @PathVariable Long id) {
        if (borrowingRecordService.deleteBorrowingRecord(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
