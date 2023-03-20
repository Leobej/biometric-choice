package com.votemetric.biometricchoice.repository;

import com.votemetric.biometricchoice.entity.Election;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionRepository extends JpaRepository<Election, Long> {
}
