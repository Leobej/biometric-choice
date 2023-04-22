package com.votemetric.biometricchoice.controller;

import com.sun.tools.jconsole.JConsoleContext;
import com.votemetric.biometricchoice.authentication.AuthenticationResponse;
import com.votemetric.biometricchoice.dto.AdminDTO;
import com.votemetric.biometricchoice.entity.AuthenticationRequest;
import com.votemetric.biometricchoice.service.AdminService;
import com.votemetric.biometricchoice.utility.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final AdminService adminService;

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("Test endpoint reached");
    }

//    @PostMapping("/authenticate")
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
//            );
//        } catch (BadCredentialsException e) {
//            throw new Exception("Incorrect username or password", e);
//        }
//
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
//        final String jwt = JwtTokenUtil.generateToken(userDetails.getUsername());
//        System.out.println(jwt);
//        return ResponseEntity.ok(new AuthenticationResponse(jwt));
//    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {

        System.out.println("Authentication request received: " + authenticationRequest);

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = JwtTokenUtil.generateToken(userDetails.getUsername());
        System.out.println("Generated token: " + jwt);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}

