package com.entreprise.msuser.services;

import com.entreprise.msuser.dtos.ApiResponse;
import com.entreprise.msuser.dtos.DepDtoRq;
import com.entreprise.msuser.dtos.DepDtoRs;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DepartementService {

    DepDtoRs getById(Long id);
    List<DepDtoRs> getAllDepartement();
    ApiResponse addDepartement(DepDtoRq depDto);
    ApiResponse deleteDepartement(Long id);
    ApiResponse updateDepartement(Long id, DepDtoRq depDto);



}
