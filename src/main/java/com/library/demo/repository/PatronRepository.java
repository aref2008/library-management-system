package com.library.demo.repository;

import com.library.demo.model.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatronRepository extends JpaRepository<Patron, Long> {
    Patron findByEmail(String email);
}
