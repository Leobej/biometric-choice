package com.votemetric.biometricchoice.modules.fingerprint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FingerprintRepository extends JpaRepository<Fingerprint, Long> {
    @Query("SELECT f FROM Fingerprint f WHERE f.id = (SELECT MAX(f2.id) FROM Fingerprint f2 WHERE f2.deviceId = :deviceId)")
    Fingerprint findLatestByDeviceId(@Param("deviceId") Long deviceId);

    //find by fingerprint fingerprint
    Optional<Fingerprint> findByFingerprint(String fingerprint);

    Optional<Fingerprint> findByVoter_VoterId(@Param("voter_id") Long voterId);
}
