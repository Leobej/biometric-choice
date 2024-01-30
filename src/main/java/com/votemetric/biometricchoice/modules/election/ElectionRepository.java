package com.votemetric.biometricchoice.modules.election;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ElectionRepository extends JpaRepository<Election, Long> {

        Page<Election> findByDescriptionContaining(String description, Pageable pageable);

        // New methods for finding elections by date ranges
        Page<Election> findByEndDateBefore(LocalDateTime endDate, Pageable pageable);
        Page<Election> findByStartDateAfter(LocalDateTime startDate, Pageable pageable);
        Page<Election> findByStartDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);


}
