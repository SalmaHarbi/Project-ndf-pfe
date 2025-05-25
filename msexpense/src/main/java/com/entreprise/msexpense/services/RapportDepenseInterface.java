package com.entreprise.msexpense.services;

import com.entreprise.msexpense.dtos.ApiResponse;
import com.entreprise.msexpense.dtos.RapportDepenseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RapportDepenseInterface {
    RapportDepenseDto getById(Long id);
    List<RapportDepenseDto> getAllRapport();
    ApiResponse addRapport(RapportDepenseDto depenseDto);
}
