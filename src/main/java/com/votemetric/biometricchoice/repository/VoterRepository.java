package com.votemetric.biometricchoice.repository;

import com.votemetric.biometricchoice.entity.Voter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoterRepository extends JpaRepository<Voter,Long> {
}
