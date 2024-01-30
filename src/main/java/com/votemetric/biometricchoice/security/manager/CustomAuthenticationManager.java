package com.votemetric.biometricchoice.security.manager;

import com.votemetric.biometricchoice.exception.ApiException;
import com.votemetric.biometricchoice.modules.admin.AdminDTO;
import com.votemetric.biometricchoice.modules.admin.AdminService;
import com.votemetric.biometricchoice.modules.user.UserDTO;
import com.votemetric.biometricchoice.modules.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@AllArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private final AdminService adminService;
    private final UserService userService; // Add UserService for mobile users
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        try {
            // Try to authenticate as an admin user
            AdminDTO adminUser = adminService.getUser(email);
            if (bCryptPasswordEncoder.matches(password, adminUser.getPassword())) {
                return new UsernamePasswordAuthenticationToken(email, null, Collections.singletonList(new SimpleGrantedAuthority(adminUser.getRole())));
            }
        } catch (ApiException e) {
            try {
                // Try to authenticate as a mobile user
                UserDTO mobileUser = userService.getUserByEmail(email);
                if (bCryptPasswordEncoder.matches(password, mobileUser.getPassword())) {
                    return new UsernamePasswordAuthenticationToken(email, null);
                }
            } catch (ApiException e1) {
                // If no mobile user is found, throw BadCredentialsException
                throw new BadCredentialsException("Invalid email or password.");
            }
        }
        throw new BadCredentialsException("Invalid email or password.");
    }

}

