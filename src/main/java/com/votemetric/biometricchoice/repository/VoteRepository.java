package com.votemetric.biometricchoice.repository;

import com.votemetric.biometricchoice.entity.Vote;
import com.votemetric.biometricchoice.entity.VoterHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
