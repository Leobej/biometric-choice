package com.votemetric.biometricchoice.modules.admin;


import com.votemetric.biometricchoice.modules.admin.Admin;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AdminRepository extends CrudRepository<Admin, Long> {
    Optional<Admin> findByUsername(String username);
}