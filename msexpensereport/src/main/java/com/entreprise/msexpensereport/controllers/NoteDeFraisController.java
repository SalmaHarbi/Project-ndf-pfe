package com.entreprise.msexpensereport.controllers;

import com.entreprise.msexpensereport.dtos.*;
import com.entreprise.msexpensereport.feign.UserRestClient;
import com.entreprise.msexpensereport.services.serviceImpl.NoteDeFraisImpl;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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



    @GetMapping("/getAll")
    public List<NdfRs> getAllNDFs() {
        List<NdfRs> ndfs = noteDeFrais.getAllNoteDeFrais();
        for (NdfRs ndf : ndfs) {
            if (ndf.getUserId() != null) {
                UserDtoRs user = userRestClient.getById(ndf.getUserId());
                ndf.setUser(user);
            }
        }
        return ndfs;
    }

    @GetMapping("/getAl")
    public List<Ndfs> getAllNDs() {
        return noteDeFrais.getAll();
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
}
