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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voter_id")
    private Voter voter;
    public Fingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }


}
