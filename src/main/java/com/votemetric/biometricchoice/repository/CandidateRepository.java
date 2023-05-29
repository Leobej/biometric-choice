package com.votemetric.biometricchoice.repository;

import com.votemetric.biometricchoice.dto.CandidateNameDTO;
import com.votemetric.biometricchoice.entity.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    @Query("SELECT c FROM Candidate c WHERE LOWER(c.firstname) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(c.lastname) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Candidate> searchCandidates(@Param("query") String query);

    @Query("SELECT new com.votemetric.biometricchoice.dto.CandidateNameDTO(c.candidateId, c.firstname, c.lastname) FROM Candidate c WHERE LOWER(c.firstname) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(c.lastname) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<CandidateNameDTO> findCandidatesByFirstnameOrLastname(@Param("query") String query);


    Page<Candidate> findByFirstnameContaining(String firstName, Pageable pageable);

    Page<Candidate> getCandidatesByFirstname(String description, Pageable pageable);
}
