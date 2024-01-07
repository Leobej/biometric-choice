package com.votemetric.biometricchoice.modules.voterhistory;

import com.votemetric.biometricchoice.modules.candidate.Candidate;
import com.votemetric.biometricchoice.modules.voter.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoterHistoryRepository extends JpaRepository<VoterHistory, Long> {
    @Query("SELECT FUNCTION('DATE_TRUNC', 'day', vh.dateVoted) as date, COUNT(vh) as totalVotes " +
            "FROM VoterHistory vh WHERE vh.election.electionId = :electionId " +
            "GROUP BY FUNCTION('DATE_TRUNC', 'day', vh.dateVoted) ORDER BY FUNCTION('DATE_TRUNC', 'day', vh.dateVoted)")
    List<Object[]> findVotingTrendsByElectionId(@Param("electionId") Long electionId);

    @Query("SELECT FUNCTION('DATE_TRUNC', 'day', vh.dateVoted) as date, vh.candidate.candidateId as candidateId, COUNT(vh) as totalVotes " +
            "FROM VoterHistory vh " +
            "WHERE vh.election.electionId = :electionId " +
            "AND vh.candidate.candidateId = :candidateId " +
            "GROUP BY FUNCTION('DATE_TRUNC', 'day', vh.dateVoted), vh.candidate.candidateId " +
            "ORDER BY FUNCTION('DATE_TRUNC', 'day', vh.dateVoted)")
    List<Object[]> findVotingTrendsByElectionIdAndCandidateId(@Param("electionId") Long electionId, @Param("candidateId") Long candidateId);

    @Query("SELECT vh.candidate.candidateId as candidateId, COUNT(vh) as voteCount " +
            "FROM VoterHistory vh WHERE vh.election.electionId = :electionId " +
            "GROUP BY vh.candidate.candidateId")
    List<Object[]> countVotesByCandidate(@Param("electionId") Long electionId);

    @Query("SELECT DISTINCT vh.candidate FROM VoterHistory vh WHERE vh.election.electionId = :electionId")
    List<Candidate> findCandidatesByElectionId(@Param("electionId") Long electionId);

@Query("SELECT DISTINCT vh.voter FROM VoterHistory vh WHERE vh.election.electionId = :electionId")
    List<Voter> findVotersByElectionId(Long electionId);
}
