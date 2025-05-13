package com.entreprise.msuser.mappers;

import com.entreprise.msuser.dtos.UserDepDto;
import com.entreprise.msuser.dtos.UserDto;
import com.entreprise.msuser.dtos.UserDtoRq;
import com.entreprise.msuser.dtos.UserDtoRs;
import com.entreprise.msuser.entities.User;
import com.entreprise.msuser.entities.Utilisateur;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UtilisateurMapper {

    @Mapping(target = "statut",defaultValue = "true")
    Utilisateur toEntity(UserDtoRq userDtoRq);

    User userDtoToUser(UserDto userDtoRq);
    UserDtoRq toDto(Utilisateur utilisateur);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Utilisateur partialUpdate(UserDtoRq userDtoRq, @MappingTarget Utilisateur utilisateur);

    Utilisateur toEntity(UserDtoRs userDtoRs);

    @Mapping(target = "statut",defaultValue = "true")
    UserDtoRs toDtoRs(Utilisateur utilisateur);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Utilisateur partialUpdate(UserDtoRs userDtoRs,@MappingTarget Utilisateur utilisateur);

    @Mapping(target = "nom")
    @Mapping(target = "prenom")
    @Mapping(target = "email")
    @Mapping(target = "role")
    UserDepDto toUtilisateurLightDto(Utilisateur utilisateur);

}