package com.entreprise.msexpense.services.serviceImpl;

import com.entreprise.msexpense.dtos.ApiResponse;
import com.entreprise.msexpense.dtos.DepenseDtoRq;
import com.entreprise.msexpense.dtos.RapportDepenseDto;
import com.entreprise.msexpense.entities.Depense;
import com.entreprise.msexpense.entities.RapportDepense;
import com.entreprise.msexpense.mappers.RapportDepenseMapper;
import com.entreprise.msexpense.repositories.RapportDepenseRepository;
import com.entreprise.msexpense.services.RapportDepenseInterface;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class RapportDepenseImpl implements RapportDepenseInterface {


    private final RapportDepenseMapper rapportDepenseMapper;
    private final RapportDepenseRepository rapportDepenseRepository;

    public RapportDepenseImpl(RapportDepenseRepository rapportDepenseRepository,
                              RapportDepenseMapper rapportDepenseMapper){
        this.rapportDepenseRepository=rapportDepenseRepository;
        this.rapportDepenseMapper=rapportDepenseMapper;
    }

    @Override
    public RapportDepenseDto getById(Long id) {
        RapportDepense rapportDepense= rapportDepenseRepository.findById(id).orElse(null);
        return rapportDepenseMapper.toDto(rapportDepense);
    }

    @Override
    public List<RapportDepenseDto> getAllRapport() {
        List<RapportDepense> rapportDepenses = rapportDepenseRepository.findAll();
        List<RapportDepenseDto> dtos=new ArrayList<>();
        rapportDepenses.forEach(e->dtos.add(rapportDepenseMapper.toDto(e)));
        return dtos;
    }

    @Override
    public ApiResponse addRapport(RapportDepenseDto depenseDto) {
        RapportDepense rapportDepense=rapportDepenseMapper.toEntity(depenseDto);
        rapportDepenseRepository.save(rapportDepense);
        return ApiResponse.builder()
                .id(rapportDepense.getId())
                .message("Rapport has been saved successfuly")
                .build();
    }

    @Override
    public ApiResponse updateRapport(Long id, RapportDepenseDto rapportDepenseDto) {
        RapportDepense rapportDepense= rapportDepenseRepository.findById(id).orElse(null);
        rapportDepenseMapper.partialUpdate(rapportDepenseDto,rapportDepense);
        rapportDepenseRepository.save(rapportDepense);
        return ApiResponse.builder()
                .id(rapportDepense.getId())
                .message("Depense has been updated successfuly")
                .build();

    }

    @Override
    public ApiResponse deleteRapport(Long id) {
        RapportDepense rapportDepense = rapportDepenseRepository.findById(id).orElse(null);
        rapportDepense.setStatut(false);
        rapportDepenseRepository.save(rapportDepense);
        return ApiResponse.builder()
                .id(rapportDepense.getId())
                .message("Depense has been deleted successfuly")
                .build();
    }
}



