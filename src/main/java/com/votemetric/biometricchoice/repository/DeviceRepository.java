package com.votemetric.biometricchoice.repository;

import com.votemetric.biometricchoice.entity.Device;
import com.votemetric.biometricchoice.entity.Election;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
}
