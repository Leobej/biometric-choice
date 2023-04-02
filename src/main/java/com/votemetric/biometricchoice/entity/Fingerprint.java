package com.votemetric.biometricchoice.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "fingerprint")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Fingerprint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fingerprint", nullable = false, length = 1024)
    private String fingerprint;

    @Column(name = "device_id", nullable = false)
    private String deviceId;


    public Fingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    // Additional metadata fields as needed

    // Constructor, getters and setters

}
