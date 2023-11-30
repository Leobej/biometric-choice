package com.votemetric.biometricchoice.interfaces;


import com.votemetric.biometricchoice.modules.admin.AdminDTO;

public interface IAdminService {
    AdminDTO getUser(Long id);

    AdminDTO getUser(String username);

    AdminDTO saveUser(AdminDTO user);

    AdminDTO loadUserByUsername(String username);
}