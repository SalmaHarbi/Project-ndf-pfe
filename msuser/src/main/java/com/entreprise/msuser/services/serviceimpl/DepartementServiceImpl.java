package com.entreprise.msuser.services.serviceimpl;

import com.entreprise.msuser.dtos.ApiResponse;
import com.entreprise.msuser.dtos.DepDtoRq;
import com.entreprise.msuser.dtos.DepDtoRs;
import com.entreprise.msuser.entities.Departement;
import com.entreprise.msuser.mappers.DepartementMapper;
import com.entreprise.msuser.repositories.DepartementRepository;
import com.entreprise.msuser.services.DepartementService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartementServiceImpl implements DepartementService {

    private final DepartementRepository departementRepository;
    private final DepartementMapper departementMapper;

    public DepartementServiceImpl(DepartementRepository departementRepository,DepartementMapper departementMapper){
        this.departementRepository=departementRepository;
        this.departementMapper=departementMapper;
    }

    @Override
    public DepDtoRs getById(Long id) {
        Departement departement= departementRepository.findById(id).orElse(null);
        return departementMapper.toDto(departement);
    }

    @Override
    public List<DepDtoRs> getAllDepartement() {
        List<Departement> departementList = departementRepository.findAll();
        List<DepDtoRs> dtoList = new ArrayList<>();
        departementList.forEach(departement -> dtoList.add(departementMapper.toDto(departement)));
        return dtoList;
    }


    @Override
    public ApiResponse addDepartement(DepDtoRq depDto) {
        Departement departement=departementMapper.toEntityAddUpdate(depDto);
        departementRepository.save(departement);
        return ApiResponse.builder()
                .id(departement.getId())
                .message("Department has been saved successfuly")
                .build();
    }

    @Override
    public ApiResponse deleteDepartement(Long id) {
        Departement departement = departementRepository.findById(id).orElse(null);
        return ApiResponse.builder()
                .id(departement.getId())
                .message("Department has been deleted successfuly")
                .build();

    }

    @Override
    public ApiResponse updateDepartement(Long id, DepDtoRq depDto) {
        Departement departement= departementRepository.findById(id).orElse(null);
        departementMapper.partialUpdate(depDto,departement);
        departementRepository.save(departement);
        return ApiResponse.builder()
                .id(departement.getId())
                .message("Department has been updated successfuly")
                .build();
    }
}




