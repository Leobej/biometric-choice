package com.votemetric.biometricchoice.repository;

import com.votemetric.biometricchoice.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
}
