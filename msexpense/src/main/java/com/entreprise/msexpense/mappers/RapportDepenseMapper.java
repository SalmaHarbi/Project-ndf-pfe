package com.entreprise.msexpense.mappers;

import com.entreprise.msexpense.dtos.RapportDepenseDto;
import com.entreprise.msexpense.entities.RapportDepense;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RapportDepenseMapper {

    RapportDepense toEntity(RapportDepenseDto rapportDepenseDto);

    RapportDepenseDto toDto(RapportDepense rapportDepense);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    RapportDepense partialUpdate(RapportDepenseDto rapportDepenseDto, @MappingTarget RapportDepense rapportDepense);
}
