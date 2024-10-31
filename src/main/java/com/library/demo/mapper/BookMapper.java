package com.library.demo.mapper;

import com.library.demo.dto.BookDTOs.*;
import com.library.demo.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    // Convert BookCreationDTO to Book entity
    public Book toEntity(BookCreationDTO dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setPublicationYear(dto.getPublicationYear());
        book.setIsbn(dto.getIsbn());
        book.setGenre(dto.getGenre());
        book.setPages(dto.getPages());
        return book;
    }

    // Convert BookUpdateDTO to Book entity
    public Book toEntity(BookUpdateDTO dto, Book existingBook) {
        existingBook.setTitle(dto.getTitle());
        existingBook.setAuthor(dto.getAuthor());
        existingBook.setPublicationYear(dto.getPublicationYear());
        existingBook.setIsbn(dto.getIsbn());
        existingBook.setGenre(dto.getGenre());
        existingBook.setPages(dto.getPages());
        return existingBook;
    }

    // Convert Book entity to BookResponseDTO
    public BookResponseDTO toResponseDTO(Book book) {
        BookResponseDTO dto = new BookResponseDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setPublicationYear(book.getPublicationYear());
        dto.setIsbn(book.getIsbn());
        dto.setGenre(book.getGenre());
        dto.setPages(book.getPages());
        return dto;
    }
}
