package com.votemetric.biometricchoice.modules.location;

import com.votemetric.biometricchoice.modules.location.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Page<Location> findByCityContaining(String description, Pageable pageable);
}
