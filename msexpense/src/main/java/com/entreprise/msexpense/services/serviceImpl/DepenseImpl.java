package com.entreprise.msexpense.services.serviceImpl;

import com.entreprise.msexpense.dtos.ApiResponse;
import com.entreprise.msexpense.dtos.DepenseDtoRq;
import com.entreprise.msexpense.dtos.DepenseDtoRs;
import com.entreprise.msexpense.dtos.Ndfs;
import com.entreprise.msexpense.entities.Depense;
import com.entreprise.msexpense.entities.Justificatif;
import com.entreprise.msexpense.feign.NdfRestClient;
import com.entreprise.msexpense.mappers.DepenseMapper;
import com.entreprise.msexpense.mappers.JustificatifMapper;
import com.entreprise.msexpense.repositories.DepenseRepository;
import com.entreprise.msexpense.services.DepenseInterface;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepenseImpl implements DepenseInterface {

    private final DepenseRepository depenseRepository;
    private final DepenseMapper depenseMapper;
    private  final JustificatifMapper justificatifMapper;
    private final NdfRestClient ndfRestClient;

    public DepenseImpl(DepenseRepository depenseRepository,
                       DepenseMapper depenseMapper,
                       JustificatifMapper justificatifMapper,
                       NdfRestClient ndfRestClient){
        this.depenseRepository=depenseRepository;
        this.depenseMapper=depenseMapper;
        this.justificatifMapper=justificatifMapper;
        this.ndfRestClient=ndfRestClient;
    }



    @Override
    public DepenseDtoRs getById(Long id) {
        Depense depense= depenseRepository.findById(id).orElse(null);
        return depenseMapper.toDto(depense);
    }

    @Override
    public List<DepenseDtoRs> getAllDepense() {
        List<Depense> depenses=depenseRepository.findAllDepenseByStatut(true);
        List<DepenseDtoRs> dtos=new ArrayList<>();
        depenses.forEach(e->dtos.add(depenseMapper.toDto(e)));
        return dtos;
    }

    @Override
    public ApiResponse addDepense(DepenseDtoRq depenseDto) {
        Depense depense=depenseMapper.toEntity(depenseDto);
        depenseRepository.save(depense);
        return ApiResponse.builder()
                .id(depense.getId())
                .message("NoteDeFrais has been saved successfuly")
                .build();
    }

    @Override
    public ApiResponse deleteDepense(Long id) {
        Depense depense = depenseRepository.findById(id).orElse(null);
        depense.setStatut(false);
        depenseRepository.save(depense);
        return ApiResponse.builder()
                .id(depense.getId())
                .message("Depense has been deleted successfuly")
                .build();
    }

    @Override
    public ApiResponse updateDepense(Long id, DepenseDtoRq depenseDto) {
        Depense depense= depenseRepository.findById(id).orElse(null);
        depenseMapper.partialUpdate(depenseDto,depense);
        depenseRepository.save(depense);
        return ApiResponse.builder()
                .id(depense.getId())
                .message("Depense has been updated successfuly")
                .build();

    }
    @Override
    public BigDecimal getMontantTotalDesDepensesActives() {
        return depenseRepository.getTotalMontantConvertiByStatut(true);
    }

    @Override
    public List<Map<String, Object>> getMontantParCategorie() {
        List<Object[]> results = depenseRepository.getDepenseMontantParCategorie();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> data = new HashMap<>();
            data.put("categorie", result[0]);
            data.put("montant", result[1]);
            response.add(data);
        }

        return response;
    }
    @Override
    public List<Map<String, Object>> getDepenseParMois() {
        List<Object[]> resultats = depenseRepository.getDepensesGroupByMonth(); // ou getDepensesGroupByYearAndMonth
        List<Map<String, Object>> reponse = new ArrayList<>();

        for (Object[] row : resultats) {
            Map<String, Object> map = new HashMap<>();

            Object moisRaw = row[0];
            int mois;

            if (moisRaw instanceof Number) {
                mois = ((Number) moisRaw).intValue(); // Gère Integer, Double, BigDecimal, etc.
            } else {
                throw new IllegalArgumentException("Type de mois inattendu : " + moisRaw.getClass());
            }

            BigDecimal montant = (BigDecimal) row[1];

            map.put("mois", mois);
            map.put("montant", montant);
            reponse.add(map);
        }

        return reponse;
    }



}
