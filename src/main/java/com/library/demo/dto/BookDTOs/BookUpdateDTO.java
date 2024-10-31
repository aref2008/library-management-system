package com.library.demo.dto.BookDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Year;

public class BookUpdateDTO {
    @NotBlank(message = "Title is mandatory")
    @Size(max = 100, message = "Title must not exceed 100 characters")
    private String title;

    @NotBlank(message = "Author is mandatory")
    @Size(max = 100, message = "Author must not exceed 100 characters")
    private String author;

    @NotNull(message = "Publication year is mandatory")
    private Year publicationYear;

    @NotBlank(message = "ISBN is mandatory")
    @Size(max = 13, message = "ISBN must not exceed 13 characters")
    private String isbn;

    @Size(max = 50, message = "Genre must not exceed 50 characters")
    private String genre;

    private Integer pages;

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Year getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Year publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }
}
