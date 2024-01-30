package com.votemetric.biometricchoice.modules.voter;

import com.votemetric.biometricchoice.modules.election.AgeDistributionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VoterRepository extends JpaRepository<Voter, Long> {
    //    boolean existsByFingerprintId(Long fingerprintId);
    Page<Voter> getVotersByFirstname(String description, Pageable pageable);

//    List<Voter> findByElections_ElectionId(Long electionId);

    Optional<Voter> findByCnp(String cnp);

    Page<Voter> findByFirstnameContainingOrLastnameContaining(String firstname, String lastname, Pageable pageable);


    Optional<Voter> findByFingerprintId(Long receivedFingerprint);

    @Query("SELECT new com.votemetric.biometricchoice.modules.election.AgeDistributionDTO(" +
            "CASE " +
            "  WHEN YEAR(CURRENT_DATE) - YEAR(v.birthdate) BETWEEN 18 AND 30 THEN '18-30' " +
            "  WHEN YEAR(CURRENT_DATE) - YEAR(v.birthdate) BETWEEN 31 AND 50 THEN '31-50' " +
            "  ELSE '51+' " +
            "END, COUNT(v)) " +
            "FROM Voter v JOIN v.voterHistories vh " +
            "WHERE vh.election.electionId = :electionId " +
            "GROUP BY CASE " +
            "  WHEN YEAR(CURRENT_DATE) - YEAR(v.birthdate) BETWEEN 18 AND 30 THEN '18-30' " +
            "  WHEN YEAR(CURRENT_DATE) - YEAR(v.birthdate) BETWEEN 31 AND 50 THEN '31-50' " +
            "  ELSE '51+' " +
            "END")
    List<AgeDistributionDTO> countAgeDistributionByElectionId(@Param("electionId") Long electionId);
}
