package com.votemetric.biometricchoice.security.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeneralAuthenticationRequest {
    private String email;
    private String password;

}
