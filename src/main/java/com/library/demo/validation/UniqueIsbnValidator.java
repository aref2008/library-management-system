package com.library.demo.validation;

import com.library.demo.repository.BookRepository; // Import your book repository
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueIsbnValidator implements ConstraintValidator<UniqueIsbn, String> {

    @Autowired
    private BookRepository bookRepository; // Autowire your repository

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {
        if (isbn == null) {
            return true; // If it's null, validation is handled by @NotBlank
        }
        return !bookRepository.existsByIsbn(isbn); // Check for uniqueness
    }
}
