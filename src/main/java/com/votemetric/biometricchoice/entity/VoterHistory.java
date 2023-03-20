package com.votemetric.biometricchoice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "votershistory")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoterHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long voterHistoryId;
    @ManyToOne
    @JoinColumn(name = "voter_id")
    private Voter voter;

    @ManyToOne
    @JoinColumn(name = "election_id")
    private Election election;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @Column(nullable = false)
    private LocalDateTime dateVoted;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(nullable = false)
    private boolean voteStatus;
}

