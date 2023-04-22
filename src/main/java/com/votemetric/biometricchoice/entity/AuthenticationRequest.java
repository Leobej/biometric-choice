package com.votemetric.biometricchoice.entity;

import com.votemetric.biometricchoice.dto.AdminDTO;

public class AuthenticationRequest {
    private String username;
    private String password;

    public AuthenticationRequest() {}

    public AuthenticationRequest(AdminDTO admin) {
        this.username = admin.getUsername();
        this.password = admin.getPassword();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

