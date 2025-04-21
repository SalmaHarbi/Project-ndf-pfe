package com.entreprise.msuser.mappers;

import com.entreprise.msuser.dtos.DepDtoRq;
import com.entreprise.msuser.dtos.DepDtoRs;
import com.entreprise.msuser.entities.Departement;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DepartementMapper {

    Departement toEntity(DepDtoRs depDto);

    Departement toEntityAddUpdate(DepDtoRq depDtoRq);

    DepDtoRs toDto(Departement departement);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Departement partialUpdate(DepDtoRq depDto, @MappingTarget Departement departement);
}