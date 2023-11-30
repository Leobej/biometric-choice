package com.votemetric.biometricchoice.modules.voterhistory;

import com.votemetric.biometricchoice.modules.voterhistory.VoterHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoterHistoryRepository extends JpaRepository<VoterHistory, Long> {
}
