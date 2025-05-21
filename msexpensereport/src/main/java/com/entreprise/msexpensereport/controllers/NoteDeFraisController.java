package com.entreprise.msexpensereport.controllers;

import com.entreprise.msexpensereport.dtos.*;
import com.entreprise.msexpensereport.feign.UserRestClient;
import com.entreprise.msexpensereport.services.serviceImpl.NoteDeFraisImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ndf")
public class NoteDeFraisController {

    private final NoteDeFraisImpl noteDeFrais;
    private final UserRestClient userRestClient;

    public NoteDeFraisController(NoteDeFraisImpl noteDeFrais,
                                 UserRestClient userRestClient){
        this.noteDeFrais=noteDeFrais;
        this.userRestClient=userRestClient;
    }

    @GetMapping("/get/{id}")
    public NdfRs getNDFById(@PathVariable("id") Long id) {
        NdfRs ndf = noteDeFrais.getById(id);

        if (ndf.getUserId() != null) {
            UserDtoRs user = userRestClient.getById(ndf.getUserId());
            ndf.setUser(user);
        }

        return ndf;
    }

   /* @GetMapping("/getAll")
    public List<NdfRs> getAllNDFs() {
        List<NdfRs> ndfs = noteDeFrais.getAllNoteDeFrais();
        for (NdfRs ndf : ndfs) {
            if (ndf.getUserId() != null) {
                UserDtoRs user = userRestClient.getById(ndf.getUserId());
                ndf.setUser(user);
            }
        }
        return ndfs;
    }*/

    @GetMapping("/getStatBrouillon")
    public List<Ndfs> getAllNDbrouillon() {
        return noteDeFrais.getAllStatutBrouillon();
    }
    @GetMapping("/getStatSoumise")
    public List<NdfRs> getAllNDsoumise() {
        return noteDeFrais.getAllStatutSoumise();
    }
    @GetMapping("/getStatApprouver")
    public List<Ndfs> getAllNDApprouver() {
        return noteDeFrais.getAllStatutApprouver();
    }
    @GetMapping("/getStatRejeter")
    public List<Ndfs> getAllNDRejeter() {
        return noteDeFrais.getAllStatutRejeter();
    }
    @GetMapping("/getStatRembourse")
    public List<Ndfs> getAllNDRembourse() {
        return noteDeFrais.getAllStatutRembourse();
    }

    @GetMapping("/getId/{id}")
    public NdfRs getId(@PathVariable("id") Long id){return noteDeFrais.getById(id);}

    @PostMapping("/add")
    public ApiResponse createNDF(@RequestBody NdfUser noteDeFrai) {
        return noteDeFrais.addNoteDeFrais(noteDeFrai);
    }

    @PutMapping("/update/{id}")
    public ApiResponse updateNDF(@PathVariable("id") Long id, @RequestBody NdfUser noteDeFrai) {
        return noteDeFrais.updateNoteDeFrais(id,noteDeFrai);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse deleteNDF(@PathVariable("id") Long id) {
        return noteDeFrais.deleteNoteDeFrais(id);
    }


    @GetMapping("/auth")
    public Authentication authentication(Authentication authentication){
        return authentication;
    }

    @PutMapping("/soumettre/{id}")
    public ResponseEntity<ApiResponse> soumettreNoteDeFrais(@PathVariable Long id) {
        return ResponseEntity.ok(noteDeFrais.changeStatutToSoumis(id));
    }
    @PutMapping("/approuver/{id}")
    public ResponseEntity<ApiResponse> approuverNoteDeFrais(@PathVariable Long id) {
        return ResponseEntity.ok(noteDeFrais.changeStatutToApprouver(id));
    }
    @PutMapping("/rejeter/{id}")
    public ResponseEntity<ApiResponse> rejeterNoteDeFrais(@PathVariable Long id) {
        return ResponseEntity.ok(noteDeFrais.changeStatutToReject(id));
    }
    @PutMapping("/rembourser/{id}")
    public ResponseEntity<ApiResponse> rembourseNoteDeFrais(@PathVariable Long id) {
        return ResponseEntity.ok(noteDeFrais.changeStatutToRembourse(id));
    }

    @GetMapping("/count-NdfS")
    public Long getNombreNotesSoumises() {
        return noteDeFrais.getNombreNotesSoumises();
    }

    @GetMapping("/count-NdfR")
    public Long getNombreNotesRembourser(){
        return noteDeFrais.getNombreNotesRembourser();
    }
    @GetMapping("/count-NdfA")
    public Long getNombreNotesApprouver(){
        return noteDeFrais.getNombreNotesApprouver();
    }
    @GetMapping("/count-NdfRej")
    public Long getNombreNotesRejeter(){
        return noteDeFrais.getNombreNotesRejeter();
    }

    @GetMapping("/notes-de-frais/mois")
    public ResponseEntity<Map<String, Map<String, Long>>> getStatsNotesDeFraisParMois() {
        return ResponseEntity.ok(noteDeFrais.getStatsParMoisEtStatut());
    }

}
