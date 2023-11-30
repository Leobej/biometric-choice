package com.votemetric.biometricchoice.modules.vote;

import com.votemetric.biometricchoice.modules.vote.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
