package com.entreprise.msuser.mappers;

import com.entreprise.msuser.dtos.KeycloakUsersList;
import com.entreprise.msuser.entities.Utilisateur;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface KeycloakUtilisateurMapper {

    @Mapping(source = "firstName", target = "firstname")
    @Mapping(source = "lastName", target = "lastname")
    @Mapping(source = "roles", target = "roles")
    Utilisateur toEntity(KeycloakUsersList keycloakUsersList);

    @Mapping(source = "firstname", target = "firstName")
    @Mapping(source = "lastname", target = "lastName")
    @Mapping(source = "roles", target = "roles")
    KeycloakUsersList toDto(Utilisateur utilisateur);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "firstName", target = "firstname")
    @Mapping(source = "lastName", target = "lastname")
    @Mapping(source = "roles", target = "roles")
    Utilisateur partialUpdate(KeycloakUsersList keycloakUsersList, @MappingTarget Utilisateur utilisateur);
}
