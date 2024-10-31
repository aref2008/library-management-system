package com.library.demo.repository;

import com.library.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
    List<Book> findByGenre(String genre);
    List<Book> findByPublicationYear(Year publicationYear);
    Book findByIsbn(String isbn);
    boolean existsByIsbn(String isbn);

}
