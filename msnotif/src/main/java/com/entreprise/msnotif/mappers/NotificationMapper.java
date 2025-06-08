package com.entreprise.msnotif.mappers;

import com.entreprise.msnotif.dtos.NotificationDto;
import com.entreprise.msnotif.dtos.NotificationEmailModel;
import com.entreprise.msnotif.dtos.NotificationKafkaDTO;
import com.entreprise.msnotif.dtos.UserDtoRs;
import com.entreprise.msnotif.entities.Notification;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationMapper {
    Notification toEntity(NotificationDto notificationDto);

    NotificationDto toDto(Notification notification);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Notification partialUpdate(NotificationDto notificationDto, @MappingTarget Notification notification);

    NotificationEmailModel toEmailModel(UserDtoRs user, NotificationKafkaDTO dto);
}