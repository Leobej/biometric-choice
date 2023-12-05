package com.votemetric.biometricchoice.interfaces;


import com.votemetric.biometricchoice.modules.user.UserDTO;

public interface IUserService {
    UserDTO getUserById(Long id);
    UserDTO getUserByEmail(String email);
    UserDTO saveUser(UserDTO userDTO);
    UserDTO updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
}
