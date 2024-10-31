package com.library.demo.controller;

import com.library.demo.dto.BookDTOs.BookCreationDTO;
import com.library.demo.dto.BookDTOs.BookResponseDTO;
import com.library.demo.dto.BookDTOs.BookUpdateDTO;
import com.library.demo.mapper.BookMapper;
import com.library.demo.model.Book;
import com.library.demo.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book API", description = "API for managing books in the library")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @Operation(summary = "Create a new book", description = "Adds a new book to the library")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Book created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(
            @Parameter(description = "Book creation details") @Valid @RequestBody BookCreationDTO bookCreationDTO) {
        Book book = bookMapper.toEntity(bookCreationDTO);
        Book savedBook = bookService.save(book);
        return new ResponseEntity<>(bookMapper.toResponseDTO(savedBook), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing book", description = "Updates details of an existing book by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Book updated successfully"),
        @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(
            @Parameter(description = "ID of the book to be updated") @PathVariable Long id,
            @Parameter(description = "Updated book details") @Valid @RequestBody BookUpdateDTO bookUpdateDTO) {
        Optional<Book> existingBookOptional = bookService.findById(id);
        if (existingBookOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Book existingBook = existingBookOptional.get();
        Book updatedBook = bookMapper.toEntity(bookUpdateDTO, existingBook);
        Book savedBook = bookService.save(updatedBook);
        return ResponseEntity.ok(bookMapper.toResponseDTO(savedBook));
    }

    @Operation(summary = "Get a book by ID", description = "Retrieves a book's details by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Book found"),
        @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(
            @Parameter(description = "ID of the book to retrieve") @PathVariable Long id) {
        Optional<Book> bookOptional = bookService.findById(id);
        if (bookOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookMapper.toResponseDTO(bookOptional.get()));
    }

    @Operation(summary = "Get all books", description = "Retrieves a list of all books in the library")
    @ApiResponse(responseCode = "200", description = "Books retrieved successfully")
    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        List<Book> books = bookService.findAll();
        List<BookResponseDTO> bookResponseDTOs = books.stream()
                .map(bookMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookResponseDTOs);
    }

    @Operation(summary = "Find books by title", description = "Search for books by their title")
    @ApiResponse(responseCode = "200", description = "Books found by title")
    @GetMapping("/search/title")
    public ResponseEntity<List<BookResponseDTO>> getBooksByTitle(
            @Parameter(description = "Title to search for") @RequestParam String title) {
        List<Book> books = bookService.findByTitle(title);
        List<BookResponseDTO> bookResponseDTOs = books.stream()
                .map(bookMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookResponseDTOs);
    }

    @Operation(summary = "Find books by author", description = "Search for books by their author")
    @ApiResponse(responseCode = "200", description = "Books found by author")
    @GetMapping("/search/author")
    public ResponseEntity<List<BookResponseDTO>> getBooksByAuthor(
            @Parameter(description = "Author's name to search for") @RequestParam String author) {
        List<Book> books = bookService.findByAuthor(author);
        List<BookResponseDTO> bookResponseDTOs = books.stream()
                .map(bookMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookResponseDTOs);
    }

    @Operation(summary = "Find books by genre", description = "Search for books by genre")
    @ApiResponse(responseCode = "200", description = "Books found by genre")
    @GetMapping("/search/genre")
    public ResponseEntity<List<BookResponseDTO>> getBooksByGenre(
            @Parameter(description = "Genre to search for") @RequestParam String genre) {
        List<Book> books = bookService.findByGenre(genre);
        List<BookResponseDTO> bookResponseDTOs = books.stream()
                .map(bookMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookResponseDTOs);
    }

    @Operation(summary = "Find books by publication year", description = "Search for books by publication year")
    @ApiResponse(responseCode = "200", description = "Books found by publication year")
    @GetMapping("/search/publication-year")
    public ResponseEntity<List<BookResponseDTO>> getBooksByPublicationYear(
            @Parameter(description = "Publication year to search for") @RequestParam Year year) {
        List<Book> books = bookService.findByPublicationYear(year);
        List<BookResponseDTO> bookResponseDTOs = books.stream()
                .map(bookMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookResponseDTOs);
    }

    @Operation(summary = "Find a book by ISBN", description = "Retrieve a book by its ISBN")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Book found by ISBN"),
        @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @GetMapping("/search/isbn")
    public ResponseEntity<BookResponseDTO> getBookByIsbn(
            @Parameter(description = "ISBN of the book") @RequestParam String isbn) {
        Book book = bookService.findByIsbn(isbn);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookMapper.toResponseDTO(book));
    }

    @Operation(summary = "Delete a book by ID", description = "Deletes a book from the library by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Book deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "ID of the book to delete") @PathVariable Long id) {
        if (!bookService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
