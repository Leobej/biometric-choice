package com.votemetric.biometricchoice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @Column(nullable = false)
    private Long voterId;
    @Column(nullable = false)
    private Long candidateId;
    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private LocalDateTime createdAt;

}
