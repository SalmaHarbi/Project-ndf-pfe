package com.entreprise.gateway.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayController {

    @Autowired
    private RouteLocator routeLocator;

    @GetMapping("/gateway/routes")
    public Object getRoutes(){
        return routeLocator.getRoutes();
    }
}
