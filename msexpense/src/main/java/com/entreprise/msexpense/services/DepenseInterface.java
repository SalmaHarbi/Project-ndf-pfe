package com.entreprise.msexpense.services;

import com.entreprise.msexpense.dtos.ApiResponse;
import com.entreprise.msexpense.dtos.DepenseDtoRq;
import com.entreprise.msexpense.dtos.DepenseDtoRs;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public interface DepenseInterface {
    DepenseDtoRs getById(Long id);
    List<DepenseDtoRs> getAllDepense();
    ApiResponse addDepense(DepenseDtoRq depenseDto);
    ApiResponse deleteDepense(Long id);
    ApiResponse updateDepense(Long id, DepenseDtoRq depenseDto);
    BigDecimal getMontantTotalDesDepensesActives();
    List<Map<String, Object>> getMontantParCategorie();
    List<Map<String, Object>> getDepenseParMois();
}
