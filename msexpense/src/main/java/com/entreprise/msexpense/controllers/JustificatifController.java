package com.entreprise.msexpense.controllers;


import com.entreprise.msexpense.dtos.ApiResponse;
import com.entreprise.msexpense.dtos.JustificatifDto;
import com.entreprise.msexpense.services.serviceImpl.JustificatifImpl;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
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
    public ApiResponse uploadFileJ(
            @RequestParam("file") MultipartFile file,
            @RequestParam("nom") String nomDepense,
            @RequestParam("dateupload") String dateString,
            @RequestParam("nomfichierordinal") String nomFichierOrdinal
    ) {
        try {
            String uploadDir = "/app/uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File dest = new File(dir, fileName);

            System.out.println("Working directory: " + System.getProperty("user.dir"));
            System.out.println("uploadDir: " + uploadDir);
            System.out.println("Destination absolute path: " + dest.getAbsolutePath());

            file.transferTo(dest);

            // Parsing la date
            LocalDateTime dateUpload = LocalDateTime.parse(dateString); // dateString doit être formaté ISO "2025-06-23T10:00:00"

            JustificatifDto justificatifDto = new JustificatifDto();
            justificatifDto.setNomfichierordinal(nomFichierOrdinal); // Prend la valeur reçue
            justificatifDto.setCheminstockage("/uploads/" + fileName);
            justificatifDto.setTypemime(file.getContentType());
            justificatifDto.setStatut(true);
            justificatifDto.setNom(nomDepense);
            justificatifDto.setDateupload(dateUpload); // Prend la date reçue

            return justificatif.UploadJustificatif(justificatifDto);

        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.builder()
                    .message("Erreur lors de l'upload : " + e.getMessage())
                    .build();
        }
    }

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadJustif(@PathVariable String filename) {
        try {
            // Utilise le dossier "uploads" à la racine du projet
            Path filePath = Paths.get("uploads").resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
