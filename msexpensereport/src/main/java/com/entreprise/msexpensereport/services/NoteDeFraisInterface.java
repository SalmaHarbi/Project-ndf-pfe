package com.entreprise.msexpensereport.services;


import com.entreprise.msexpensereport.dtos.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface NoteDeFraisInterface {
    Ndfs getById(Long id);
   List<Ndfs> getAllNoteDeFrais();
    ApiResponse addNoteDeFrais(NdfUser noteDeFraisDto);
    ApiResponse deleteNoteDeFrais(Long id);
    ApiResponse updateNoteDeFrais(Long id,NdfUser noteDeFraisDto);
    List<Ndfs> getAllStatutBrouillon();
    List<Ndfs> getAllStatutApprouver();
    List<Ndfs> getAllStatutRejeter();
    List<Ndfs> getAllStatutRembourse();
    List<Ndfs> getAllStatutSoumise();
    ApiResponse changeStatutToSoumis(Long id);
    ApiResponse changeStatutToApprouver(Long id);
    ApiResponse changeStatutToReject(Long id);
    ApiResponse changeStatutToRembourse(Long id);
    Long getNombreNotesSoumises();
    Long getNombreNotesRembourser();
    Long getNombreNotesApprouver();
    Long getNombreNotesRejeter();
    Map<String, Map<String, Long>> getStatsParMoisEtStatut();

}
