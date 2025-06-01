package com.entreprise.msexpense.controllers;

import com.entreprise.msexpense.dtos.ApiResponse;
import com.entreprise.msexpense.dtos.RapportDepenseDto;
import com.entreprise.msexpense.services.serviceImpl.RapportDepenseImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rapport")
public class RapportController {

    private final RapportDepenseImpl rapportDepenseImpl;

    public RapportController(RapportDepenseImpl rapportDepenseImpl){
        this.rapportDepenseImpl=rapportDepenseImpl;
    }

    @GetMapping("/get/{id}")
    public RapportDepenseDto getRapportById(@PathVariable("id") Long id) {
        RapportDepenseDto rapportDepenseDto = rapportDepenseImpl.getById(id);
        return rapportDepenseDto;
    }

    @GetMapping("/getAll")
    public List<RapportDepenseDto> getAllRapport() {
        List<RapportDepenseDto> rapportDepenseDtos=rapportDepenseImpl.getAllRapport();
        return rapportDepenseDtos;
    }


    @PostMapping("/add")
    public ApiResponse createRapport(@RequestBody RapportDepenseDto rapportDepenseDto) {
        return rapportDepenseImpl.addRapport(rapportDepenseDto);
    }

    @PutMapping("/update/{id}")
    public ApiResponse updateRapport(@PathVariable("id") Long id, @RequestBody RapportDepenseDto rapportDepenseDto) {
        return rapportDepenseImpl.updateRapport(id,rapportDepenseDto);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse deleteRapport(@PathVariable("id") Long id) {
        return rapportDepenseImpl.deleteRapport(id);
    }
}






