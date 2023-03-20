package com.votemetric.biometricchoice.repository;

import com.votemetric.biometricchoice.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location,Long> {
}
