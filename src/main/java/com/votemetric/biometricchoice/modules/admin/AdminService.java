package com.votemetric.biometricchoice.modules.admin;

import com.votemetric.biometricchoice.exception.ApiException;
import com.votemetric.biometricchoice.interfaces.IAdminService;
import com.votemetric.biometricchoice.mapper.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        return mapper.convertToType(user.get(), AdminDTO.class);
    }

    @Override
    public AdminDTO getUser(String email) {
        Admin user = adminRepository.findByEmail(email).orElseThrow(() -> new ApiException("User with this name doesn't exist", HttpStatus.NOT_FOUND));
        return mapper.convertToType(user, AdminDTO.class);
    }

    @Override
    public AdminDTO saveUser(AdminDTO adminDTO) {
        Admin user = mapper.convertToType(adminDTO, Admin.class);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        adminRepository.save(user);
        return mapper.convertToType(user, AdminDTO.class);
    }

    public AdminDTO loadUserByEmail(String email) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));

        return mapper.convertToType(admin, AdminDTO.class);
    }
    public String getUserRole(String email) {
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));

        return admin.getRole();
    }

}
