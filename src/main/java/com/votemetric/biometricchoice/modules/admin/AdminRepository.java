package com.votemetric.biometricchoice.modules.admin;


import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AdminRepository extends CrudRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);
}