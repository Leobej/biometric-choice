package com.votemetric.biometricchoice.repository;

import com.votemetric.biometricchoice.entity.Fingerprint;
import com.votemetric.biometricchoice.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FingerprintRepository  extends JpaRepository<Fingerprint,Long> {
}
