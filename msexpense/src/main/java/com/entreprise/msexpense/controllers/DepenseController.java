package com.entreprise.msexpense.controllers;


import com.entreprise.msexpense.dtos.*;
import com.entreprise.msexpense.feign.NdfRestClient;
import com.entreprise.msexpense.services.serviceImpl.DepenseImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/depense")
public class DepenseController {

    private final NdfRestClient ndfRestClient;
    private final DepenseImpl depenseImpl;


    public DepenseController(NdfRestClient ndfRestClient, DepenseImpl depenseImpl){
        this.ndfRestClient=ndfRestClient;
        this.depenseImpl = depenseImpl;
    }

    @GetMapping("/get/{id}")
    public DepenseDtoRs getDepenseById(@PathVariable("id") Long id) {
        DepenseDtoRs depenseDtoRs = depenseImpl.getById(id);

       /* if (depenseDtoRs.getNdfId() != null) {
            Ndfs ndfs = ndfRestClient.getId(depenseDtoRs.getNdfId());
            depenseDtoRs.setNdfs(ndfs);
        }*/
        return depenseDtoRs;
    }

    @GetMapping("/getAll")
    public List<DepenseDtoRs> getAllDepenses() {

        List<DepenseDtoRs> depenseDtoRs=depenseImpl.getAllDepense();
      /*  for (DepenseDtoRs depense : depenseDtoRs){
            if(depense.getNdfId() != null){
                Ndfs ndfRs = ndfRestClient.getId(depense.getNdfId());
                depense.setNdfs(ndfRs);
            }
        }*/
        return depenseDtoRs;
    }


    @PostMapping("/add")
    public ApiResponse createDepense(@RequestBody DepenseDtoRq depenseDto) {
        return depenseImpl.addDepense(depenseDto);
    }

    @PutMapping("/update/{id}")
    public ApiResponse updateDepense(@PathVariable("id") Long id, @RequestBody DepenseDtoRq depenseDto) {
        return depenseImpl.updateDepense(id,depenseDto);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse deleteDepense(@PathVariable("id") Long id) {
        return depenseImpl.deleteDepense(id);
    }

    @GetMapping("/auth")
    public Authentication authentication(Authentication authentication){
        return authentication;
    }

    @GetMapping("/montant-total")
    public BigDecimal getMontantTotalDesDepensesActives() {
        return depenseImpl.getMontantTotalDesDepensesActives();
    }

    @GetMapping("/depenses-par-categorie")
    public ResponseEntity<List<Map<String, Object>>> getMontantParCategorie() {
        return ResponseEntity.ok(depenseImpl.getMontantParCategorie());
    }

    @GetMapping("/depenses-mensuelles")
    public ResponseEntity<List<Map<String, Object>>> getDepensesParMois() {
        List<Map<String, Object>> stats = depenseImpl.getDepenseParMois();
        return ResponseEntity.ok(stats);
    }
}
