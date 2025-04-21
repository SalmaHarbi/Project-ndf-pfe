package com.entreprise.msexpense.feign;

import com.entreprise.msexpense.dtos.NdfRs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msexpensereport")
public interface NdfRestClient {

    @GetMapping("/user/get/{id}")
    NdfRs getById(@PathVariable Long id);

    @GetMapping("/user/getAll")
    List<NdfRs> getAllNoteDeFrais();
}
