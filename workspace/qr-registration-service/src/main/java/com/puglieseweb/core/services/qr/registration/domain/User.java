package com.puglieseweb.core.services.qr.registration.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
class User {

    private String firstName;
    private String surname;
    private UUID userId;

}