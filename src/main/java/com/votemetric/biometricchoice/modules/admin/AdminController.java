package com.votemetric.biometricchoice.modules.admin;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/user")
public class AdminController {

    AdminService adminService;

    @GetMapping("/{id}")
    public ResponseEntity<String> findById(@PathVariable Long id) {
        return new ResponseEntity<>(adminService.getUser(id).getEmail(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> createUser(@Valid @RequestBody AdminDTO adminDTO) {
        adminService.saveUser(adminDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/user-role/{email}")
    public String getUserRole(@PathVariable String email) {
        return adminService.getUserRole(email);
    }



}