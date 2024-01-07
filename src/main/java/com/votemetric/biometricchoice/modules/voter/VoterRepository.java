package com.votemetric.biometricchoice.modules.voter;

import com.votemetric.biometricchoice.modules.voter.Voter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoterRepository extends JpaRepository<Voter, Long> {
    //    boolean existsByFingerprintId(Long fingerprintId);
    Page<Voter> getVotersByFirstname(String description, Pageable pageable);

//    List<Voter> findByElections_ElectionId(Long electionId);

    Optional<Voter> findByCnp(String cnp);

    Page<Voter> findByFirstnameContainingOrLastnameContaining(String firstname, String lastname, Pageable pageable);


}
