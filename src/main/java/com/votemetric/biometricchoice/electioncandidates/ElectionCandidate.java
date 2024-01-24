//package com.votemetric.biometricchoice.electioncandidates;
//
//import com.votemetric.biometricchoice.modules.candidate.Candidate;
//import com.votemetric.biometricchoice.modules.election.Election;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.io.Serializable;
//
//@Entity
//@Table(name = "election_candidates")
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//public class ElectionCandidate implements Serializable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "election_candidate_id")
//    private long electionCandidateId;
//
//    @Column(name = "election_id", nullable = false)
//    private long electionId;
//
//    @Column(name = "candidate_id", nullable = false)
//    private long candidateId;
//
//    // Relationships
//    @ManyToOne
//    @JoinColumn(name = "candidate_id", referencedColumnName = "candidate_id", insertable = false, updatable = false)
//    private Candidate candidate;
//
//    @ManyToOne
//    @JoinColumn(name = "election_id", referencedColumnName = "election_id", insertable = false, updatable = false)
//    private Election election;
//
//    public ElectionCandidate(long electionId, long candidateId) {
//        this.electionId = electionId;
//        this.candidateId = candidateId;
//    }
//
//}
//
