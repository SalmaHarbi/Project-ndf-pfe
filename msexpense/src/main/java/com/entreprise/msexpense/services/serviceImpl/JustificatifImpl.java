package com.entreprise.msexpense.services.serviceImpl;

import com.entreprise.msexpense.dtos.ApiResponse;
import com.entreprise.msexpense.dtos.JustificatifDto;
import com.entreprise.msexpense.entities.Depense;
import com.entreprise.msexpense.entities.Justificatif;
import com.entreprise.msexpense.mappers.JustificatifMapper;
import com.entreprise.msexpense.repositories.DepenseRepository;
import com.entreprise.msexpense.repositories.JustificatifRepository;
import com.entreprise.msexpense.services.JustificatifInterface;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JustificatifImpl implements JustificatifInterface {


    private final JustificatifRepository justificatifRepository;
    private final JustificatifMapper justificatifMapper;
    private final DepenseRepository depenseRepository;

    public JustificatifImpl(JustificatifRepository justificatifRepository,JustificatifMapper justificatifMapper, DepenseRepository depenseRepository){
        this.justificatifRepository=justificatifRepository;
        this.justificatifMapper=justificatifMapper;
        this.depenseRepository=depenseRepository;
    }


    @Override
    public JustificatifDto getById(Long id) {
        Justificatif justificatif= justificatifRepository.findById(id).orElse(null);
        return justificatifMapper.toDto(justificatif);
    }

    @Override
    public List<JustificatifDto> getAllJustificatif() {
        List<Justificatif> justificatifs = justificatifRepository.findAllJustificatifByStatut(true);

        justificatifs.sort(Comparator.comparing(
                justificatif -> {
                    Depense depense = justificatif.getDepense();
                    return (depense != null && depense.getCategorie() != null) ? depense.getCategorie().name() : null;
                },
                Comparator.nullsLast(String::compareTo)
        ));

        return justificatifs.stream()
                .map(justificatifMapper::toDto)
                .collect(Collectors.toList());
    }



    @Override
    public ApiResponse UploadJustificatif(JustificatifDto justificatifDto) {
        Justificatif justificatif = justificatifMapper.toEntity(justificatifDto);

        Depense depense = depenseRepository.findByNom(justificatifDto.getNom())
                .orElseThrow(() -> new RuntimeException("Dépense avec ce nom introuvable"));

        justificatif.setDepense(depense);
        justificatifRepository.save(justificatif);

        return ApiResponse.builder()
                .id(justificatif.getId())
                .message("Justificatif a été enregistré avec succès")
                .build();
    }



    @Override
    public ApiResponse deleteJustificatif(Long id) {
        Justificatif justificatif = justificatifRepository.findById(id).orElse(null);
        justificatif.setStatut(false);
        justificatifRepository.save(justificatif);
        return ApiResponse.builder()
                .id(justificatif.getId())
                .message("Justificatif has been deleted successfuly")
                .build();
    }

    @Override
    public ApiResponse updateJustificatif(Long id, JustificatifDto justificatifDto) {
        Justificatif justificatif= justificatifRepository.findById(id).orElse(null);
        justificatifMapper.partialUpdate(justificatifDto,justificatif);
        justificatifRepository.save(justificatif);
        return ApiResponse.builder()
                .id(justificatif.getId())
                .message("Depense has been updated successfuly")
                .build();

    }
    }









