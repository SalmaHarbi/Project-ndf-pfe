package com.entreprise.msexpensereport.feign;


import com.entreprise.msexpensereport.dtos.DepenseDtoRs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msexpense")
public interface DepenseRestClient {

    @GetMapping("/depense/get/{id}")
    DepenseDtoRs getById(@PathVariable Long id);

    @GetMapping("/depense/getAll")
    List<DepenseDtoRs> getAllDepenses();


}
