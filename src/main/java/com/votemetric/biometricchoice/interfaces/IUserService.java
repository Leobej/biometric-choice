package com.votemetric.biometricchoice.interfaces;


import com.votemetric.biometricchoice.dto.UserDTO;

public interface IUserService {
    UserDTO getUser(Long id);

    UserDTO getUser(String username);

    UserDTO saveUser(UserDTO user);

}