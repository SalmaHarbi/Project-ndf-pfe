package com.entreprise.msexpensereport.mappers;


import com.entreprise.msexpensereport.dtos.NdfRs;
import com.entreprise.msexpensereport.dtos.NdfUser;
import com.entreprise.msexpensereport.entities.NoteDeFrais;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface NoteDeFraisMapper {

    NoteDeFrais toEntity(NdfUser noteDeFraisDto);

    NdfRs toDto(NoteDeFrais noteDeFrais);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    NoteDeFrais partialUpdate(NdfUser noteDeFraisDto, @MappingTarget NoteDeFrais noteDeFrais);


}
