package com.entreprise.msexpense.mappers;

import com.entreprise.msexpense.dtos.JustificatifDto;
import com.entreprise.msexpense.entities.Justificatif;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface JustificatifMapper {
    @Mapping(target = "statut",defaultValue = "true")
    Justificatif toEntity(JustificatifDto justificatifDto);

    JustificatifDto toDto(Justificatif justificatif);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Justificatif partialUpdate(JustificatifDto justificatifDto, @MappingTarget Justificatif justificatif);
}