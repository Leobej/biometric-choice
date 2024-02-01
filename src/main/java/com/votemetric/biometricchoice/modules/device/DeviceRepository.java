package com.votemetric.biometricchoice.modules.device;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Page<Device> findByNameContainingOrTypeContainingOrStatusContaining(String name, String type, String status, Pageable pageable);
    Device findByName(String deviceName);

    @Query("SELECT d.location.locationId FROM Device d WHERE d.name = :deviceName")
    Long findLocationIdByDeviceName(@Param("deviceName") String deviceName);
}
