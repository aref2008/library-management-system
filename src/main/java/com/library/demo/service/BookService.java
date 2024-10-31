package com.library.demo.service;

import com.library.demo.model.Book;
import com.library.demo.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save(Book book) {
        // Check for existing ISBN
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new IllegalArgumentException("A book with this ISBN already exists.");
        }
        return bookRepository.save(book);
    }
    

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public List<Book> findByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    public List<Book> findByGenre(String genre) {
        return bookRepository.findByGenre(genre);
    }

    public List<Book> findByPublicationYear(Year publicationYear) {
        return bookRepository.findByPublicationYear(publicationYear);
    }

    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
