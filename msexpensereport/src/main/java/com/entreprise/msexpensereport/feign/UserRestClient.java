package com.entreprise.msexpensereport.feign;


import com.entreprise.msexpensereport.dtos.UserDtoRs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(name = "msuser")
public interface UserRestClient {

    @GetMapping("/user/get/{id}")
    UserDtoRs getById(@PathVariable Long id);

    @GetMapping("/user/getAll")
    List<UserDtoRs> getAllUtilisateur();
}
