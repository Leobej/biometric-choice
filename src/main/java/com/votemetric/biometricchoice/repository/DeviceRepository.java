package com.votemetric.biometricchoice.repository;

import com.votemetric.biometricchoice.entity.Device;
import com.votemetric.biometricchoice.entity.Election;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    Page<Election> findByTypeContaining(String type, Pageable pageable);
}
