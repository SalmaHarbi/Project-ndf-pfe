package com.entreprise.msexpense.feign;

import com.entreprise.msexpense.configuration.FeignClientInterceptor;
import com.entreprise.msexpense.dtos.Ndfs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msexpensereport", configuration = FeignClientInterceptor.class)
public interface NdfRestClient {

    @GetMapping("/ndf/getId/{id}")
    Ndfs getId(@PathVariable Long id);

    @GetMapping("/ndf/getAl")
    List<Ndfs> getAll();
}
