package com.votemetric.biometricchoice.modules.election;

import com.votemetric.biometricchoice.modules.election.Election;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionRepository extends JpaRepository<Election, Long> {

        Page<Election> findByDescriptionContaining(String description, Pageable pageable);


}
