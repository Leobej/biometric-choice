package com.votemetric.biometricchoice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "voters")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Voter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long voterId;
    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String lastname;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String cnp;
    @Column(nullable = false, unique = true)
    private String fingerPrint;
    @Column(nullable = false)
    private String createdAt;

}