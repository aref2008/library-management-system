package com.library.demo.mapper;

import com.library.demo.dto.PatronDTOs.PatronCreationDTO;
import com.library.demo.dto.PatronDTOs.PatronResponseDTO;
import com.library.demo.dto.PatronDTOs.PatronUpdateDTO;
import com.library.demo.model.Patron;

public class PatronMapper {

    public static Patron toEntity(PatronCreationDTO dto) {
        Patron patron = new Patron();
        patron.setName(dto.getName());
        patron.setEmail(dto.getEmail());
        patron.setPhone(dto.getPhone());
        return patron;
    }

    public static PatronResponseDTO toResponseDTO(Patron patron) {
        PatronResponseDTO responseDTO = new PatronResponseDTO();
        responseDTO.setId(patron.getId());
        responseDTO.setName(patron.getName());
        responseDTO.setEmail(patron.getEmail());
        responseDTO.setPhone(patron.getPhone());
        return responseDTO;
    }

    public static void updatePatronFromDTO(PatronUpdateDTO dto, Patron patron) {
        if (dto.getName() != null) {
            patron.setName(dto.getName());
        }
        if (dto.getEmail() != null) {
            patron.setEmail(dto.getEmail());
        }
        if (dto.getPhone() != null) {
            patron.setPhone(dto.getPhone());
        }
    }
}
