package com.votemetric.biometricchoice.modules.voter;

import com.votemetric.biometricchoice.modules.voterhistory.VoterHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
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

    @Column(nullable = false, unique = true)
    private String cnp;

    @Column
    private String createdAt;

    @Column
    private LocalDate birthdate;

    @Column(nullable = false, name = "fingerprint_id")
    private Long fingerprintId;

    // Relationships
//    @OneToMany(mappedBy = "voter")
//    private List<Election> elections;

    @OneToMany(mappedBy = "voter")
    private List<VoterHistory> voterHistories;
}
