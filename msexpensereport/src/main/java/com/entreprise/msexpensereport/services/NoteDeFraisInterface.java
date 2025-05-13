package com.entreprise.msexpensereport.services;


import com.entreprise.msexpensereport.dtos.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoteDeFraisInterface {
    NdfRs getById(Long id);
    List<NdfRs> getAllNoteDeFrais();
    ApiResponse addNoteDeFrais(NdfUser noteDeFraisDto);
    ApiResponse deleteNoteDeFrais(Long id);
    ApiResponse updateNoteDeFrais(Long id,NdfUser noteDeFraisDto);
    List<Ndfs> getAll();
    Ndfs getId(Long id);
}
