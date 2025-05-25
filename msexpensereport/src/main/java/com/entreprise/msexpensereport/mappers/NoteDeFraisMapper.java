package com.entreprise.msexpensereport.mappers;


import com.entreprise.msexpensereport.dtos.NdfUser;
import com.entreprise.msexpensereport.dtos.Ndfs;
import com.entreprise.msexpensereport.entities.NoteDeFrais;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface NoteDeFraisMapper {

    NoteDeFrais toEntity(NdfUser noteDeFraisDto);


    Ndfs toDtos(NoteDeFrais noteDeFrais);

    List<Ndfs> toDtoss(List<NoteDeFrais> noteDeFraisList);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    NoteDeFrais partialUpdate(NdfUser noteDeFraisDto, @MappingTarget NoteDeFrais noteDeFrais);


}
