package com.votemetric.biometricchoice.modules.location;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Page<Location> findByCityContaining(String description, Pageable pageable);

    @Query("SELECT l FROM Location l WHERE " +
            "l.city LIKE %:searchTerm% OR " +
            "l.postalCode LIKE %:searchTerm% OR " +
            "l.country LIKE %:searchTerm% OR " +
            "l.street LIKE %:searchTerm% OR " +
            "l.number LIKE %:searchTerm%")
    Page<Location> findBySearchTerm(String searchTerm, Pageable pageable);

}
