package com.votemetric.biometricchoice.repository;

import com.votemetric.biometricchoice.entity.Fingerprint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FingerprintRepository extends JpaRepository<Fingerprint, Long> {
}
