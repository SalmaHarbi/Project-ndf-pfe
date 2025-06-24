package com.entreprise.msexpense.feign;

import com.entreprise.msexpense.configuration.FeignClientInterceptor;
import com.entreprise.msexpense.dtos.Ndfs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msexpensereport", configuration = FeignClientInterceptor.class)
public interface NdfRestClient {

    @GetMapping("/ndf/get/{id}")
    Ndfs getById(@PathVariable("id") Long id);

    @GetMapping("/ndf/getAll")
    List<Ndfs> getAllNoteDeFrais();


}
