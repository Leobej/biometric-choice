package com.votemetric.biometricchoice.modules.device;

import com.votemetric.biometricchoice.modules.device.Device;
import com.votemetric.biometricchoice.modules.election.Election;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.messaging.MessageHeaders;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    Page<Election> findByTypeContaining(String type, Pageable pageable);

    Page<Device> findByNameContainingOrTypeContainingOrStatusContaining(String name, String type, String status, Pageable pageable);

    Device findByName(String deviceName);
}
