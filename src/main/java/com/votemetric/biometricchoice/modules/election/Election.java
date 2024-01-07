package com.votemetric.biometricchoice.modules.election;

import com.votemetric.biometricchoice.modules.candidate.Candidate;
import com.votemetric.biometricchoice.modules.electionresult.ElectionResult;
import com.votemetric.biometricchoice.modules.voter.Voter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "elections")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Election {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long electionId;

//    @ManyToOne
//    @JoinColumn(name = "voter_id")
//    private Voter voter;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String location;

    private Boolean active;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    // Relationships
    @OneToMany(mappedBy = "election")
    private List<ElectionResult> electionResults;

    @ManyToMany
    @JoinTable(
            name = "election_candidates",
            joinColumns = @JoinColumn(name = "election_id"),
            inverseJoinColumns = @JoinColumn(name = "candidate_id")
    )
    private List<Candidate> candidates;
}
