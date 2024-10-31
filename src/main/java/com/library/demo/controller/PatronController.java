package com.library.demo.controller;

import com.library.demo.dto.PatronDTOs.PatronCreationDTO;
import com.library.demo.dto.PatronDTOs.PatronResponseDTO;
import com.library.demo.dto.PatronDTOs.PatronUpdateDTO;
import com.library.demo.service.PatronService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {
    private final PatronService patronService;

    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }

    /**
     * Creates a new patron.
     *
     * @param patronCreationDTO the patron creation details
     * @return the created patron response DTO
     */
    @Operation(summary = "Create a new patron", description = "Creates a new patron with the provided details.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Patron created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping
    public ResponseEntity<PatronResponseDTO> createPatron(@RequestBody PatronCreationDTO patronCreationDTO) {
        PatronResponseDTO createdPatron = patronService.createPatron(patronCreationDTO);
        return new ResponseEntity<>(createdPatron, HttpStatus.CREATED);
    }

    /**
     * Retrieves all patrons.
     *
     * @return a list of all patrons
     */
    @Operation(summary = "Get all patrons", description = "Retrieves a list of all patrons.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of patrons retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<PatronResponseDTO>> getAllPatrons() {
        List<PatronResponseDTO> patrons = patronService.getAllPatrons();
        return ResponseEntity.ok(patrons);
    }

    /**
     * Retrieves a patron by their ID.
     *
     * @param id the ID of the patron to retrieve
     * @return the patron response DTO if found
     */
    @Operation(summary = "Get a patron by ID", description = "Retrieves a specific patron by their ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Patron found"),
        @ApiResponse(responseCode = "404", description = "Patron not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PatronResponseDTO> getPatronById(@Parameter(description = "ID of the patron to retrieve") @PathVariable Long id) {
        return patronService.getPatronById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Updates an existing patron by their ID.
     *
     * @param id  the ID of the patron to update
     * @param patronUpdateDTO the patron update details
     * @return the updated patron response DTO if found
     */
    @Operation(summary = "Update a patron", description = "Updates the details of an existing patron by their ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Patron updated successfully"),
        @ApiResponse(responseCode = "404", description = "Patron not found"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PatronResponseDTO> updatePatron(
            @Parameter(description = "ID of the patron to update") @PathVariable Long id,
            @RequestBody PatronUpdateDTO patronUpdateDTO) {
        return patronService.updatePatron(id, patronUpdateDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Deletes a patron by their ID.
     *
     * @param id the ID of the patron to delete
     * @return no content if the patron was deleted, or not found if they don't exist
     */
    @Operation(summary = "Delete a patron", description = "Deletes a specific patron by their ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Patron deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Patron not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatron(@Parameter(description = "ID of the patron to delete") @PathVariable Long id) {
        if (patronService.deletePatron(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
