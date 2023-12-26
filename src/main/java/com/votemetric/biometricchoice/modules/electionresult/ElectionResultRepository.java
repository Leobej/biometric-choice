package com.votemetric.biometricchoice.modules.electionresult;

import com.votemetric.biometricchoice.modules.electionresult.ElectionResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ElectionResultRepository extends JpaRepository<ElectionResult, Long> {
    List<ElectionResult> findByElection_ElectionId(Long electionId);
}
