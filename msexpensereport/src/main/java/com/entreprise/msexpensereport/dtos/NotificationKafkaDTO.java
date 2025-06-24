package com.entreprise.msexpensereport.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationKafkaDTO {
    private Long noteId;
    private String type;
    private Long userId;
}
