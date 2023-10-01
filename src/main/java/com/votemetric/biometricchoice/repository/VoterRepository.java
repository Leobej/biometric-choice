package com.votemetric.biometricchoice.repository;

import com.votemetric.biometricchoice.entity.Voter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoterRepository extends JpaRepository<Voter, Long> {
    //    boolean existsByFingerprintId(Long fingerprintId);
    Page<Voter> getVotersByFirstname(String description, Pageable pageable);
    Optional<Voter> findByCnp(String cnp);
}
