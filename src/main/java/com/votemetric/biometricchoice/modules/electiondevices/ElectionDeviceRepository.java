package com.votemetric.biometricchoice.modules.electiondevices;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ElectionDeviceRepository extends JpaRepository<ElectionDevice, Long> {
    List<ElectionDevice> findAllByElection_ElectionId(Long electionId);
}

