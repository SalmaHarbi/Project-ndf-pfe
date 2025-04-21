package com.entreprise.msexpensereport.services;


import com.entreprise.msexpensereport.dtos.ApiResponse;
import com.entreprise.msexpensereport.dtos.NdfRq;
import com.entreprise.msexpensereport.dtos.NdfRs;
import com.entreprise.msexpensereport.dtos.NdfUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoteDeFraisInterface {
    NdfRs getById(Long id);
    List<NdfRs> getAllNoteDeFrais();
    ApiResponse addNoteDeFrais(NdfUser noteDeFraisDto);
    ApiResponse deleteNoteDeFrais(Long id);
    ApiResponse updateNoteDeFrais(Long id,NdfUser noteDeFraisDto);
}
