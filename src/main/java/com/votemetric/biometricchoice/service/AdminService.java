package com.votemetric.biometricchoice.service;

import com.votemetric.biometricchoice.dto.AdminDTO;
import com.votemetric.biometricchoice.entity.Admin;
import com.votemetric.biometricchoice.exception.ApiException;
import com.votemetric.biometricchoice.interfaces.IAdminService;
import com.votemetric.biometricchoice.mapper.Mapper;
import com.votemetric.biometricchoice.repository.AdminRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService implements IAdminService {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Mapper mapper;

    public AdminService(AdminRepository adminRepository, BCryptPasswordEncoder bCryptPasswordEncoder, Mapper mapper) {
        this.adminRepository = adminRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mapper = mapper;
    }

    @Override
    public AdminDTO getUser(Long id) {
        Optional<Admin> user = adminRepository.findById(id);
        return mapper.convertToType(user, AdminDTO.class);
    }

    @Override
    public AdminDTO getUser(String username) {
        Admin user = adminRepository.findByUsername(username).orElseThrow(() -> new ApiException("User with this name doesn't exist", HttpStatus.NOT_FOUND));
        return mapper.convertToType(user, AdminDTO.class);
    }

    @Override
    public AdminDTO saveUser(AdminDTO adminDTO) {
        Admin user = mapper.convertToType(adminDTO, Admin.class);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        adminRepository.save(user);
        return mapper.convertToType(user, AdminDTO.class);
    }

}
