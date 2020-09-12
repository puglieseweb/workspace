package com.puglieseweb.app.billswallet.controller;

import com.puglieseweb.app.billswallet.services.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/register")
public class RegistrationController {
    final static Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public void register(UUID custerId, UUID businessId){
        LOGGER.info("Registering customerId {} with businessID {}", custerId, businessId);
//        registrationService.sendRegistration(custerId, businessId);
    }


}
