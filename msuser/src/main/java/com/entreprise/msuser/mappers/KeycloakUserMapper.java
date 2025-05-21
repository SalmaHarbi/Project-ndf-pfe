package com.entreprise.msuser.mappers;

import com.entreprise.msuser.dtos.UserDtoRsKey;
import com.entreprise.msuser.entities.KeycloakUser;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface KeycloakUserMapper {
    KeycloakUser toEntity(UserDtoRsKey userDtoRsKey);

    UserDtoRsKey toDto(KeycloakUser keycloakUser);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    KeycloakUser partialUpdate(UserDtoRsKey userDtoRsKey, @MappingTarget KeycloakUser keycloakUser);






}