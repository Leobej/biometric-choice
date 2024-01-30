package com.votemetric.biometricchoice.modules.device;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Page<Device> findByNameContainingOrTypeContainingOrStatusContaining(String name, String type, String status, Pageable pageable);
    Device findByName(String deviceName);
}
