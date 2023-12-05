package com.votemetric.biometricchoice.interfaces;


import com.votemetric.biometricchoice.modules.admin.AdminDTO;

public interface IAdminService {
    AdminDTO getUser(Long id);

    AdminDTO getUser(String email);

    AdminDTO saveUser(AdminDTO user);

    AdminDTO loadUserByEmail(String username);
}