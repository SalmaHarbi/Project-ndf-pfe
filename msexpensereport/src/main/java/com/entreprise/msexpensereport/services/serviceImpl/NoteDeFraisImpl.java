package com.entreprise.msexpensereport.services.serviceImpl;

import com.entreprise.msexpensereport.dtos.*;
import com.entreprise.msexpensereport.entities.Enum.Statut;
import com.entreprise.msexpensereport.entities.NoteDeFrais;
import com.entreprise.msexpensereport.mappers.NoteDeFraisMapper;
import com.entreprise.msexpensereport.repositories.NoteDeFraisRepository;
import com.entreprise.msexpensereport.services.NoteDeFraisInterface;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class  NoteDeFraisImpl implements NoteDeFraisInterface {

    private final NoteDeFraisRepository noteDeFraisRepository;
    private final NoteDeFraisMapper noteDeFraisMapper;

    public NoteDeFraisImpl(NoteDeFraisRepository noteDeFraisRepository,
                           NoteDeFraisMapper noteDeFraisMapper){
        this.noteDeFraisMapper=noteDeFraisMapper;
        this.noteDeFraisRepository=noteDeFraisRepository;
    }

    @Override
    public Ndfs getById(Long id) {
        NoteDeFrais noteDeFrais= noteDeFraisRepository.findById(id).orElse(null);
        return noteDeFraisMapper.toDtos(noteDeFrais);
    }


    @Override
    public List<Ndfs> getAllNoteDeFrais() {
        List<NoteDeFrais> noteDeFraisList = noteDeFraisRepository.findAllNoteDeFraisByStatut(true);
        List<Ndfs> ndfRs = new ArrayList<>();
        noteDeFraisList.forEach(e->ndfRs.add(noteDeFraisMapper.toDtos(e)));
        return ndfRs;
    }


    @Override
    public ApiResponse addNoteDeFrais(NdfUser noteDeFraisDto) {
        NoteDeFrais noteDeFrais=noteDeFraisMapper.toEntity(noteDeFraisDto);
        noteDeFraisRepository.save(noteDeFrais);
        return ApiResponse.builder()
                .id(noteDeFrais.getId())
                .message("NoteDeFrais has been saved successfuly")
                .build();
    }

    @Override
    public ApiResponse deleteNoteDeFrais(Long id) {
        NoteDeFrais noteDeFrais = noteDeFraisRepository.findById(id).orElse(null);
        noteDeFrais.setStatutDisable(false);
        noteDeFraisRepository.save(noteDeFrais);
        return ApiResponse.builder()
                .id(noteDeFrais.getId())
                .message("Note de frais has been deleted successfuly")
                .build();
    }

    @Override
    public ApiResponse updateNoteDeFrais(Long id, NdfUser noteDeFraisDto) {
        NoteDeFrais noteDeFrais= noteDeFraisRepository.findById(id).orElse(null);
        if (noteDeFrais == null) {
            return ApiResponse.builder()
                    .message("Note de frais not found")
                    .build();
        }
        noteDeFraisMapper.partialUpdate(noteDeFraisDto,noteDeFrais);
        noteDeFraisRepository.save(noteDeFrais);
        return ApiResponse.builder()
                .id(noteDeFrais.getId())
                .message("Note de frais has been updated successfuly")
                .build();
    }

    @Override
    public List<Ndfs> getAllStatutBrouillon() {
        List<NoteDeFrais> notes = noteDeFraisRepository.findAllNoteDeFraisByStatutAndStatutDisable(true, Statut.brouillon);
        List<Ndfs> ndfRs = new ArrayList<>();
        notes.forEach(e->ndfRs.add(noteDeFraisMapper.toDtos(e)));
        return ndfRs;
    }
    @Override
    public List<Ndfs> getAllStatutApprouver() {
        List<NoteDeFrais> notes = noteDeFraisRepository.findAllNoteDeFraisByStatutAndStatutDisable(true, Statut.approuvé);
        List<Ndfs> ndfRs = new ArrayList<>();
        notes.forEach(e->ndfRs.add(noteDeFraisMapper.toDtos(e)));
        return ndfRs;
    }
    @Override
    public List<Ndfs> getAllStatutRejeter() {
        List<NoteDeFrais> notes = noteDeFraisRepository.findAllNoteDeFraisByStatutAndStatutDisable(true, Statut.rejeté);
        List<Ndfs> ndfRs = new ArrayList<>();
        notes.forEach(e->ndfRs.add(noteDeFraisMapper.toDtos(e)));
        return ndfRs;
    }
    @Override
    public List<Ndfs> getAllStatutRembourse() {
        List<NoteDeFrais> notes = noteDeFraisRepository.findAllNoteDeFraisByStatutAndStatutDisable(true, Statut.remboursé);
        List<Ndfs> ndfRs = new ArrayList<>();
        notes.forEach(e->ndfRs.add(noteDeFraisMapper.toDtos(e)));
        return ndfRs;
    }
    @Override
    public List<Ndfs> getAllStatutSoumise() {
        List<NoteDeFrais> notes = noteDeFraisRepository.findAllNoteDeFraisByStatutAndStatutDisable(true, Statut.soumis);
        List<Ndfs> ndfRs = new ArrayList<>();
        notes.forEach(e->ndfRs.add(noteDeFraisMapper.toDtos(e)));
        return ndfRs;
    }
    @Override
    public ApiResponse changeStatutToSoumis(Long id) {
        NoteDeFrais noteDeFrais = noteDeFraisRepository.findById(id).orElse(null);

        if (noteDeFrais == null) {
            return ApiResponse.builder()
                    .message("Note de frais introuvable")
                    .build();
        }

        if (noteDeFrais.getStatut() != Statut.brouillon) {
            return ApiResponse.builder()
                    .id(noteDeFrais.getId())
                    .message("Impossible de soumettre : la note n'est pas en brouillon.")
                    .build();
        }

        noteDeFrais.setStatut(Statut.soumis);
        noteDeFrais.setDatesoumission(LocalDateTime.now());
        noteDeFraisRepository.save(noteDeFrais);

        return ApiResponse.builder()
                .id(noteDeFrais.getId())
                .message("Le statut de la note a été changé en 'soumis'")
                .build();
    }

    @Override
    public ApiResponse changeStatutToApprouver(Long id) {
        NoteDeFrais noteDeFrais = noteDeFraisRepository.findById(id).orElse(null);

        if (noteDeFrais == null) { return ApiResponse.builder()
                .message("Note de frais introuvable")
                .build(); }

        if (noteDeFrais.getStatut() != Statut.soumis) { return ApiResponse.builder()
                    .id(noteDeFrais.getId())
                    .message("Impossible de soumettre : la note n'est pas en Approuver.")
                    .build(); }

        noteDeFrais.setStatut(Statut.approuvé);
        noteDeFrais.setDatesoumission(LocalDateTime.now());
        noteDeFraisRepository.save(noteDeFrais);

        return ApiResponse.builder()
                .id(noteDeFrais.getId())
                .message("Le statut de la note a été changé en 'approuvé'")
                .build();
    }

    @Override
    public ApiResponse changeStatutToReject(Long id) {
        NoteDeFrais noteDeFrais = noteDeFraisRepository.findById(id).orElse(null);

        if (noteDeFrais == null) {return ApiResponse.builder()
                    .message("Note de frais introuvable")
                    .build(); }

        if (noteDeFrais.getStatut() != Statut.soumis) { return ApiResponse.builder()
                    .id(noteDeFrais.getId())
                    .message("Impossible de soumettre : la note n'est pas en rejeter.")
                    .build(); }

        noteDeFrais.setStatut(Statut.rejeté);
        noteDeFrais.setDatesoumission(LocalDateTime.now());
        noteDeFraisRepository.save(noteDeFrais);

        return ApiResponse.builder()
                .id(noteDeFrais.getId())
                .message("Le statut de la note a été changé en 'rejeté'")
                .build();
    }

    @Override
    public ApiResponse changeStatutToRembourse(Long id) {
        NoteDeFrais noteDeFrais = noteDeFraisRepository.findById(id).orElse(null);

        if (noteDeFrais == null) { return ApiResponse.builder()
                    .message("Note de frais introuvable")
                    .build(); }

        if (noteDeFrais.getStatut() != Statut.approuvé) {
            return ApiResponse.builder()
                    .id(noteDeFrais.getId())
                    .message("Impossible de soumettre : la note n'est pas en remboursé.")
                    .build(); }

        noteDeFrais.setStatut(Statut.remboursé);
        noteDeFrais.setDatesoumission(LocalDateTime.now());
        noteDeFraisRepository.save(noteDeFrais);

        return ApiResponse.builder()
                .id(noteDeFrais.getId())
                .message("Le statut de la note a été changé en 'remboursé'")
                .build();
    }

    @Override
    public Long getNombreNotesSoumises() {
        return noteDeFraisRepository.countNoteDeFraisByStatut(Statut.soumis);
    }

    @Override
    public Long getNombreNotesRembourser(){
        return noteDeFraisRepository.countNoteDeFraisByStatut(Statut.remboursé);
    }

    @Override
    public Long getNombreNotesApprouver() {
        return noteDeFraisRepository.countNoteDeFraisByStatut(Statut.approuvé);
    }

    @Override
    public Long getNombreNotesRejeter() {
        return noteDeFraisRepository.countNoteDeFraisByStatut(Statut.rejeté);
    }

    @Override
    public Map<String, Map<String, Long>> getStatsParMoisEtStatut() {
        List<Object[]> results = noteDeFraisRepository.countByMonthAndStatut();

        Map<String, Map<String, Long>> stats = new TreeMap<>();

        for (Object[] row : results) {
            int annee = ((BigDecimal) row[0]).intValue();
            int mois = ((BigDecimal) row[1]).intValue();
            Statut statut = Statut.valueOf(row[2].toString());
            Number numberValue = (Number) row[3];
            long total = numberValue.longValue();


            String moisAnnee = String.format("%d-%02d", annee, mois);

            stats.computeIfAbsent(moisAnnee, k -> new HashMap<>());
            stats.get(moisAnnee).put(statut.name(), total);
        }
        return stats;
    }


}
