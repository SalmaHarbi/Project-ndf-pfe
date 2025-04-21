package com.entreprise.msexpense.services;

import com.entreprise.msexpense.dtos.ApiResponse;
import com.entreprise.msexpense.dtos.JustificatifDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JustificatifInterface {
    JustificatifDto getById(Long id);
    List<JustificatifDto> getAllJustificatif();
    ApiResponse UploadJustificatif(JustificatifDto justificatifDto);
    ApiResponse deleteJustificatif(Long id);
    ApiResponse updateJustificatif(Long id, JustificatifDto justificatifDto);

}
