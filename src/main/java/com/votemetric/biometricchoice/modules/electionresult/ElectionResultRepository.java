package com.votemetric.biometricchoice.modules.electionresult;

import com.votemetric.biometricchoice.modules.electionresult.ElectionResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionResultRepository extends JpaRepository<ElectionResult, Long> {
}
