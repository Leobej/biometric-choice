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
    private long voterId;
    @Column(nullable = false)
    private Long electionId;
    @Column(nullable = false)
    private Long candidateId;
    @Column(nullable = false)
    private LocalDateTime dateVoted;
    @Column(nullable = false, unique = true)
    private String location;
    @Column(nullable = false, unique = true)
    private boolean voteStatus;

}
