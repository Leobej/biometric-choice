package com.votemetric.biometricchoice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "fingerprint")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Fingerprint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fingerprint", nullable = false, length = 1024)
    private String fingerprint;

    public Fingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    // Additional metadata fields as needed

    // Constructor, getters and setters

}
