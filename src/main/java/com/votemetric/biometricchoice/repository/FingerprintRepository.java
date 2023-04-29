package com.votemetric.biometricchoice.repository;

import com.votemetric.biometricchoice.entity.Fingerprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FingerprintRepository extends JpaRepository<Fingerprint, Long> {
    @Query("SELECT f FROM Fingerprint f WHERE f.id = (SELECT MAX(f2.id) FROM Fingerprint f2 WHERE f2.deviceId = :deviceId)")
    Fingerprint findLatestByDeviceId(@Param("deviceId") String deviceId);
}
