package com.entreprise.msexpensereport.services.serviceImpl;

import com.entreprise.msexpensereport.dtos.*;
import com.entreprise.msexpensereport.entities.NoteDeFrais;
import com.entreprise.msexpensereport.feign.UserRestClient;
import com.entreprise.msexpensereport.mappers.NoteDeFraisMapper;
import com.entreprise.msexpensereport.repositories.NoteDeFraisRepository;
import com.entreprise.msexpensereport.services.NoteDeFraisInterface;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NoteDeFraisImpl implements NoteDeFraisInterface {

    private final NoteDeFraisRepository noteDeFraisRepository;
    private final NoteDeFraisMapper noteDeFraisMapper;
    private final UserRestClient userRestClient;

    public NoteDeFraisImpl(NoteDeFraisRepository noteDeFraisRepository,
                           NoteDeFraisMapper noteDeFraisMapper,
                           UserRestClient userRestClient){
        this.noteDeFraisMapper=noteDeFraisMapper;
        this.noteDeFraisRepository=noteDeFraisRepository;
        this.userRestClient=userRestClient;
    }

    @Override
    public NdfRs getById(Long id) {
        NoteDeFrais noteDeFrais= noteDeFraisRepository.findById(id).orElse(null);
        return noteDeFraisMapper.toDto(noteDeFrais);
    }
    @Override
    public Ndfs getId(Long id) {
        NoteDeFrais noteDeFrais= noteDeFraisRepository.findById(id).orElse(null);
        return noteDeFraisMapper.toDtos(noteDeFrais);
    }

    @Override
    public List<NdfRs> getAllNoteDeFrais() {
        List<NoteDeFrais> noteDeFraisList = noteDeFraisRepository.findAllNoteDeFraisByStatut(true);
        List<NdfRs> ndfRs = new ArrayList<>();
        noteDeFraisList.forEach(e->ndfRs.add(noteDeFraisMapper.toDto(e)));
        return ndfRs;
    }


    @Override
    public ApiResponse addNoteDeFrais(NdfUser noteDeFraisDto) {
        NoteDeFrais noteDeFrais=noteDeFraisMapper.toEntity(noteDeFraisDto);
        noteDeFraisRepository.save(noteDeFrais);
        return ApiResponse.builder()
                .id(noteDeFrais.getId())
                .message("NoteDeFrais has been saved successfuly")
                .build();
    }

    @Override
    public ApiResponse deleteNoteDeFrais(Long id) {
        NoteDeFrais noteDeFrais = noteDeFraisRepository.findById(id).orElse(null);
        noteDeFrais.setStatutDisable(false);
        noteDeFraisRepository.save(noteDeFrais);
        return ApiResponse.builder()
                .id(noteDeFrais.getId())
                .message("Note de frais has been deleted successfuly")
                .build();
    }

    @Override
    public ApiResponse updateNoteDeFrais(Long id, NdfUser noteDeFraisDto) {
        NoteDeFrais noteDeFrais= noteDeFraisRepository.findById(id).orElse(null);
        if (noteDeFrais == null) {
            return ApiResponse.builder()
                    .message("Note de frais not found")
                    .build();
        }
        noteDeFraisMapper.partialUpdate(noteDeFraisDto,noteDeFrais);
        noteDeFraisRepository.save(noteDeFrais);
        return ApiResponse.builder()
                .id(noteDeFrais.getId())
                .message("Note de frais has been updated successfuly")
                .build();
    }

    @Override
    public List<Ndfs> getAll() {
        List<NoteDeFrais> noteDeFraisList = noteDeFraisRepository.findAllNoteDeFraisByStatut(true);
        List<Ndfs> ndfRs = new ArrayList<>();
        noteDeFraisList.forEach(e->ndfRs.add(noteDeFraisMapper.toDtos(e)));
        return ndfRs;
    }


}
