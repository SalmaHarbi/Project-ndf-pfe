package com.entreprise.msnotif.services;

import com.entreprise.msnotif.dtos.UserDtoRs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {
    @Autowired
    private RestTemplate restTemplate;

    public UserDtoRs getUserById(Long userId) {
        String url = "http://localhost:8081/user/get/" + userId;
        return restTemplate.getForObject(url, UserDtoRs.class);
    }
}