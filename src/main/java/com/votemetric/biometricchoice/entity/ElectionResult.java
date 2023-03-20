package com.votemetric.biometricchoice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "electionresults")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ElectionResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long electionResultsId;

    @ManyToOne
    @JoinColumn(name = "election_id")
    private Election election;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @Column(nullable = false)
    private Long votes;

    @Column(nullable = false)
    private Double percentage;
}

