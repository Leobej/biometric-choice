package com.votemetric.biometricchoice.repository;



import com.votemetric.biometricchoice.entity.Admin;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Admin, Long> {
    Optional<Admin> findByUsername(String username);
}