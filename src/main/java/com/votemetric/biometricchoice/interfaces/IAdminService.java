package com.votemetric.biometricchoice.interfaces;


import com.votemetric.biometricchoice.dto.AdminDTO;

public interface IAdminService {
    AdminDTO getUser(Long id);

    AdminDTO getUser(String username);

    AdminDTO saveUser(AdminDTO user);
}