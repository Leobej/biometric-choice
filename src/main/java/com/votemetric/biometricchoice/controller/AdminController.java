package com.votemetric.biometricchoice.controller;


import com.votemetric.biometricchoice.dto.AdminDTO;
import com.votemetric.biometricchoice.service.AdminService;
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
        return new ResponseEntity<>(adminService.getUser(id).getUsername(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> createUser(@Valid @RequestBody AdminDTO user) {
        adminService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/user-role/{username}")
    public String getUserRole(@PathVariable String username) {
        return adminService.getUserRole(username);
    }



}