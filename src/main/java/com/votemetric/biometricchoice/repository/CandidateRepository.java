package com.votemetric.biometricchoice.repository;

import com.votemetric.biometricchoice.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

//    @Query(value = "INSERT INTO candidate (firstname, lastname, created_at, party, position, image) VALUES (:firstname, :lastname, :created_at, :party, :position, :image)", nativeQuery = true)
//    void saveCandidate(@Param("firstname") String firstname, @Param("lastname") String lastname, @Param("created_at") LocalDateTime created_at, @Param("party") String party, @Param("position") String position, @Param("image") byte[] image);

}
