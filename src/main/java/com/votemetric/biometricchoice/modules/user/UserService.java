package com.votemetric.biometricchoice.modules.user;

import com.votemetric.biometricchoice.exception.UserNotFoundException;
import com.votemetric.biometricchoice.interfaces.IUserService;
import com.votemetric.biometricchoice.mapper.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Mapper mapper;

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        return mapper.convertToType(user, UserDTO.class);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
        return mapper.convertToType(user, UserDTO.class);
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        User user = mapper.convertToType(userDTO, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return mapper.convertToType(savedUser, UserDTO.class);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        // Add other fields to update as necessary

        User updatedUser = userRepository.save(existingUser);
        return mapper.convertToType(updatedUser, UserDTO.class);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.deleteById(id);
    }
}