package com.votemetric.biometricchoice.modules.voter;

import com.votemetric.biometricchoice.modules.election.Election;
import com.votemetric.biometricchoice.modules.voterhistory.VoterHistory;
import com.votemetric.biometricchoice.modules.fingerprint.Fingerprint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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

    @Column(nullable = false)
    private String createdAt;

    @Column(nullable = false, name = "fingerprint_id")
    private Long fingerprintId;

    // Relationships
    @OneToMany(mappedBy = "voter")
    private List<Election> elections;

    @OneToMany(mappedBy = "voter")
    private List<VoterHistory> voterHistories;
}
