package com.votemetric.biometricchoice.modules.voterhistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoterHistoryRepository extends JpaRepository<VoterHistory, Long> {
    @Query("SELECT FUNCTION('DATE_TRUNC', 'day', vh.dateVoted) as date, COUNT(vh) as totalVotes " +
            "FROM VoterHistory vh WHERE vh.election.electionId = :electionId " +
            "GROUP BY FUNCTION('DATE_TRUNC', 'day', vh.dateVoted) ORDER BY FUNCTION('DATE_TRUNC', 'day', vh.dateVoted)")
    List<Object[]> findVotingTrendsByElectionId(@Param("electionId") Long electionId);

    @Query("SELECT FUNCTION('DATE_TRUNC', 'day', vh.dateVoted) as date, COUNT(vh) " +
            "FROM VoterHistory vh " +
            "WHERE vh.election.electionId = :electionId " +
            "AND (:candidateId IS NULL OR vh.candidate.candidateId = :candidateId) " +
            "GROUP BY FUNCTION('DATE_TRUNC', 'day', vh.dateVoted) " +
            "ORDER BY FUNCTION('DATE_TRUNC', 'day', vh.dateVoted)")
    List<Object[]> findVotingTrendsByElectionIdAndCandidateId(@Param("electionId") Long electionId, @Param("candidateId") Long candidateId);


}
