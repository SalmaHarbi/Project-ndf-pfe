package com.entreprise.msexpense.controllers;


import com.entreprise.msexpense.dtos.*;
import com.entreprise.msexpense.feign.NdfRestClient;
import com.entreprise.msexpense.services.serviceImpl.DepenseImpl;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/depense")
public class DepenseController {

    private final DepenseImpl depense;
    private final NdfRestClient ndfRestClient;
    private final DepenseImpl depenseImpl;


    public DepenseController(DepenseImpl depense,
                             NdfRestClient ndfRestClient, DepenseImpl depenseImpl
                             ){
        this.depense=depense;
        this.ndfRestClient=ndfRestClient;
        this.depenseImpl = depenseImpl;
    }

    @GetMapping("/get/{id}")
    public DepenseDtoRs getDepenseById(@PathVariable("id") Long id) {
        DepenseDtoRs depenseDtoRs = depense.getById(id);

        if (depenseDtoRs.getNdfId() != null) {
            Ndfs ndfs = ndfRestClient.getId(depenseDtoRs.getNdfId());
            depenseDtoRs.setNdfs(ndfs);
        }
        return depenseDtoRs;
    }

    @GetMapping("/getAll")
    public List<DepenseDtoRs> getAllDepenses() {

        List<DepenseDtoRs> depenseDtoRs=depenseImpl.getAllDepense();
        for (DepenseDtoRs depense : depenseDtoRs){
            if(depense.getNdfId() != null){
                Ndfs ndfRs = ndfRestClient.getId(depense.getNdfId());
                depense.setNdfs(ndfRs);
            }
        }
        return depenseDtoRs;
    }




    @PostMapping("/add")
    public ApiResponse createDepense(@RequestBody DepenseDtoRq depenseDto) {
        return depense.addDepense(depenseDto);
    }

    @PutMapping("/update/{id}")
    public ApiResponse updateDepense(@PathVariable("id") Long id, @RequestBody DepenseDtoRq depenseDto) {
        return depense.updateDepense(id,depenseDto);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse deleteDepense(@PathVariable("id") Long id) {
        return depense.deleteDepense(id);
    }

    @GetMapping("/auth")
    public Authentication authentication(Authentication authentication){
        return authentication;
    }

}
