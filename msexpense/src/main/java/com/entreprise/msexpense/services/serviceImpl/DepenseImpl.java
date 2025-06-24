package com.entreprise.msexpense.services.serviceImpl;

import com.entreprise.msexpense.dtos.ApiResponse;
import com.entreprise.msexpense.dtos.DepenseDtoRq;
import com.entreprise.msexpense.dtos.DepenseDtoRs;
import com.entreprise.msexpense.entities.Depense;
import com.entreprise.msexpense.mappers.DepenseMapper;
import com.entreprise.msexpense.repositories.DepenseRepository;
import com.entreprise.msexpense.services.DepenseInterface;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepenseImpl implements DepenseInterface {

    private final DepenseRepository depenseRepository;
    private final DepenseMapper depenseMapper;

    public DepenseImpl(DepenseRepository depenseRepository,
                       DepenseMapper depenseMapper){
        this.depenseRepository=depenseRepository;
        this.depenseMapper=depenseMapper;
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

    private void calculerMontantConverti(Depense depense) {
        if (depense.getMontant() != null && depense.getTauxchange() != null) {
            depense.setMontantconverti(depense.getMontant().multiply(depense.getTauxchange()));
        } else {
            depense.setMontantconverti(BigDecimal.ZERO);
        }
    }

    @Override
    public ApiResponse addDepense(DepenseDtoRq depenseDto) {
        Depense depense = depenseMapper.toEntity(depenseDto);

        calculerMontantConverti(depense); // <--- Appel ici

        depenseRepository.save(depense);
        return ApiResponse.builder()
                .id(depense.getId())
                .message("NoteDeFrais has been saved successfuly")
                .build();
    }

    @Override
    public ApiResponse updateDepense(Long id, DepenseDtoRq depenseDto) {
        Depense depense = depenseRepository.findById(id).orElse(null);
        depenseMapper.partialUpdate(depenseDto, depense);

        calculerMontantConverti(depense); // <--- Appel ici aussi

        depenseRepository.save(depense);
        return ApiResponse.builder()
                .id(depense.getId())
                .message("Depense has been updated successfuly")
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
        List<Object[]> resultats = depenseRepository.getDepensesGroupByMonth();
        List<Map<String, Object>> reponse = new ArrayList<>();

        for (Object[] row : resultats) {
            Map<String, Object> map = new HashMap<>();

            Object moisRaw = row[0];
            int mois;

            if (moisRaw instanceof Number) {
                mois = ((Number) moisRaw).intValue();
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

    @Override
    public List<Depense> findByNdfId(Long ndfId) {
        return depenseRepository.findByNdfId(ndfId);
    }
}
