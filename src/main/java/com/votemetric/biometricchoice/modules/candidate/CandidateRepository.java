package com.votemetric.biometricchoice.modules.candidate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    @Query("SELECT c FROM Candidate c WHERE LOWER(c.firstname) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(c.lastname) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Candidate> searchCandidates(@Param("query") String query);

    @Query("SELECT new com.votemetric.biometricchoice.modules.candidate.CandidateNameDTO(c.candidateId, c.firstname, c.lastname) FROM Candidate c WHERE LOWER(c.firstname) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(c.lastname) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<CandidateNameDTO> findCandidatesByFirstnameOrLastname(@Param("query") String query);

    Page<Candidate> findByFirstnameContaining(String firstName, Pageable pageable);

    List<Candidate> findByElections_ElectionId(Long electionId);

    Page<Candidate> getCandidatesByFirstname(String description, Pageable pageable);

    Page<Candidate> findByFirstnameContainingOrLastnameContaining(String firstname, String lastname, Pageable pageable);

    @Query("SELECT c FROM Candidate c " +
            "JOIN c.elections e " +
            "JOIN e.electionDevices ed " +
            "JOIN ed.device d " +
            "WHERE d.name = :deviceName AND e.active = true")
    List<Candidate> findCandidatesByDeviceName(@Param("deviceName") String deviceName);

}
