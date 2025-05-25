package com.entreprise.msexpense.mappers;

import com.entreprise.msexpense.dtos.DepenseDtoRq;
import com.entreprise.msexpense.dtos.DepenseDtoRs;
import com.entreprise.msexpense.entities.Depense;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DepenseMapper {

    Depense toEntity(DepenseDtoRq depenseDto);

    DepenseDtoRs toDto(Depense depense);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Depense partialUpdate(DepenseDtoRq depenseDto, @MappingTarget Depense depense);
}