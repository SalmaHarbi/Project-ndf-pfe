package com.entreprise.msuser.mappers;

import com.entreprise.msuser.dtos.UserDepDto;
import com.entreprise.msuser.dtos.UserDtoRq;
import com.entreprise.msuser.dtos.UserDtoRs;
import com.entreprise.msuser.entities.Utilisateur;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UtilisateurMapper {

    Utilisateur toEntity(UserDtoRq userDtoRq);

    UserDtoRq toDto(Utilisateur utilisateur);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Utilisateur partialUpdate(UserDtoRq userDtoRq, @MappingTarget Utilisateur utilisateur);

    Utilisateur toEntity(UserDtoRs userDtoRs);

    UserDtoRs toDtoRs(Utilisateur utilisateur);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Utilisateur partialUpdate(UserDtoRs userDtoRs,@MappingTarget Utilisateur utilisateur);

    @Mapping(target ="firstname")
    @Mapping(target = "lastname")
    @Mapping(target = "email")
    @Mapping(target = "roles")
    UserDepDto toUtilisateurLightDto(Utilisateur utilisateur);

}