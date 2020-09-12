package com.puglieseweb.app.billswallet.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class Registration {
    UUID customerId;
    UUID businessId;
    String registrationData;
    String registrationLocation;

    public Registration(UUID customerId, UUID businessId, String registrationData, String registrationLocation) {
        this.customerId = customerId;
        this.businessId = businessId;
        this.registrationData = registrationData;
        this.registrationLocation = registrationLocation;
    }
}
