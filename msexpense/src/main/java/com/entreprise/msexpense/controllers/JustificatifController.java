package com.entreprise.msexpense.controllers;


import com.entreprise.msexpense.dtos.ApiResponse;
import com.entreprise.msexpense.dtos.JustificatifDto;
import com.entreprise.msexpense.services.serviceImpl.JustificatifImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/justif")
public class JustificatifController {

    private final JustificatifImpl justificatif;

    public JustificatifController(JustificatifImpl justificatif){
        this.justificatif=justificatif;
    }
    @GetMapping("/get/{id}")
    public JustificatifDto getJustificatifById(@PathVariable("id") Long id) {
        return justificatif.getById(id);
    }

    @GetMapping("/getAll")
    public List<JustificatifDto> getAllJustificatif() {
        return justificatif.getAllJustificatif();
    }

    @PostMapping("/add")
    public ApiResponse uploadJustificatif(@RequestBody JustificatifDto justificatifDto) {
        return justificatif.UploadJustificatif(justificatifDto);
    }


    @DeleteMapping("/delete/{id}")
    public ApiResponse deleteJustificatif(@PathVariable("id") Long id) {
        return justificatif.deleteJustificatif(id);
    }

    @PutMapping("/update/{id}")
    public ApiResponse updateJustificatif(@PathVariable("id") Long id, @RequestBody JustificatifDto justificatifDto) {
        return justificatif.updateJustificatif(id,justificatifDto);
    }



    @PostMapping("/upload")
    public ApiResponse uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("nom") String nomDepense // ou tout autre champ utile
    ) {
        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String uploadDir = "uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();
            file.transferTo(new File(uploadDir + fileName));

            // On prépare le DTO avec les informations nécessaires
            JustificatifDto justificatifDto = new JustificatifDto();
            justificatifDto.setNomfichierordinal(file.getOriginalFilename());
            justificatifDto.setCheminstockage("/uploads/" + fileName);
            justificatifDto.setTypemime(file.getContentType());
            justificatifDto.setStatut(true);
            justificatifDto.setNom(nomDepense);

            // Utilisation du service qui utilise le mapper pour convertir DTO -> Entité puis sauvegarder
            return justificatif.UploadJustificatif(justificatifDto);

        } catch (Exception e) {
            return ApiResponse.builder()
                    .message("Erreur lors de l'upload : " + e.getMessage())
                    .build();
        }
    }

}
