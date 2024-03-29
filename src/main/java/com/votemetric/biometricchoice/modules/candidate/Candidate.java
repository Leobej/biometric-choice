package com.votemetric.biometricchoice.modules.candidate;

import com.votemetric.biometricchoice.modules.election.Election;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "candidates")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long candidateId;

    @Column(nullable = false, unique = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    private String party;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Lob
    @Column(nullable = true, columnDefinition = "bytea")
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] image;

    // Relationships
    @ManyToMany(mappedBy = "candidates")
    private List<Election> elections;

}
