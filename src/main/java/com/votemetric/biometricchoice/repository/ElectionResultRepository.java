package com.votemetric.biometricchoice.repository;

import com.votemetric.biometricchoice.entity.ElectionResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionResultRepository extends JpaRepository<ElectionResult,Long> {
}
