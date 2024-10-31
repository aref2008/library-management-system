package com.library.demo.service;

import com.library.demo.dto.PatronDTOs.PatronCreationDTO;
import com.library.demo.dto.PatronDTOs.PatronResponseDTO;
import com.library.demo.dto.PatronDTOs.PatronUpdateDTO;
import com.library.demo.mapper.PatronMapper;
import com.library.demo.model.Patron;
import com.library.demo.repository.PatronRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatronService {
    private final PatronRepository patronRepository;

    public PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    public PatronResponseDTO createPatron(PatronCreationDTO patronCreationDTO) {
        Patron patron = PatronMapper.toEntity(patronCreationDTO);
        Patron savedPatron = patronRepository.save(patron);
        return PatronMapper.toResponseDTO(savedPatron);
    }

    public List<PatronResponseDTO> getAllPatrons() {
        return patronRepository.findAll()
                .stream()
                .map(PatronMapper::toResponseDTO)
                .toList();
    }

    public Optional<PatronResponseDTO> getPatronById(Long id) {
        return patronRepository.findById(id)
                .map(PatronMapper::toResponseDTO);
    }

    public Optional<PatronResponseDTO> updatePatron(Long id, PatronUpdateDTO patronUpdateDTO) {
        return patronRepository.findById(id)
                .map(existingPatron -> {
                    PatronMapper.updatePatronFromDTO(patronUpdateDTO, existingPatron);
                    Patron updatedPatron = patronRepository.save(existingPatron);
                    return PatronMapper.toResponseDTO(updatedPatron);
                });
    }

    public boolean deletePatron(Long id) {
        if (patronRepository.existsById(id)) {
            patronRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
