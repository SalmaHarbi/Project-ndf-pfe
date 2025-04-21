package com.entreprise.msexpense.controllers;


import com.entreprise.msexpense.dtos.ApiResponse;
import com.entreprise.msexpense.dtos.JustificatifDto;
import com.entreprise.msexpense.services.serviceImpl.JustificatifImpl;
import org.springframework.web.bind.annotation.*;

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

}
