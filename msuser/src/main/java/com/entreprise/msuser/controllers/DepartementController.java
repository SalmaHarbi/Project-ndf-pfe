package com.entreprise.msuser.controllers;

import com.entreprise.msuser.dtos.ApiResponse;
import com.entreprise.msuser.dtos.DepDtoRq;
import com.entreprise.msuser.dtos.DepDtoRs;
import com.entreprise.msuser.services.DepartementService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dep")
public class DepartementController {

    private final DepartementService departementService;

    public DepartementController(DepartementService departementService){
        this.departementService=departementService;
    }

    @GetMapping("/get/{id}")
    public DepDtoRs getDepById(@PathVariable("id") Long id) {
        return departementService.getById(id);
    }

    @GetMapping("/getAll")
    public List<DepDtoRs> getAllUse() {
        return departementService.getAllDepartement();
    }

    @PostMapping("/add")
    public ApiResponse createDepartement(@RequestBody DepDtoRq depDto) {
        return departementService.addDepartement(depDto);
    }

    @PutMapping("/update/{id}")
    public ApiResponse updateDepartement(@PathVariable("id") Long id, @RequestBody DepDtoRq depDto) {
        return departementService.updateDepartement(id,depDto);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse deleteDepartement(@PathVariable("id") Long id) {
        return departementService.deleteDepartement(id);
    }
    @GetMapping("/auth")
    public Authentication authentication(Authentication authentication){
        return authentication;
    }


}