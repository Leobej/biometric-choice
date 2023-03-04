package com.votemetric.biometricchoice.service;



import com.votemetric.biometricchoice.dto.UserDTO;
import com.votemetric.biometricchoice.entity.User;
import com.votemetric.biometricchoice.exception.ApiException;
import com.votemetric.biometricchoice.interfaces.IUserService;
import com.votemetric.biometricchoice.mapper.Mapper;
import com.votemetric.biometricchoice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Mapper mapper;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, Mapper mapper) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mapper = mapper;
    }

    @Override
    public UserDTO getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return mapper.convertToType(user, UserDTO.class);
    }

    @Override
    public UserDTO getUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ApiException("User with this name doesn't exist", HttpStatus.NOT_FOUND));
        return mapper.convertToType(user, UserDTO.class);
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        User user = mapper.convertToType(userDTO, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return mapper.convertToType(user, UserDTO.class);
    }

}
