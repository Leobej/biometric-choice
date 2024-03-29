package com.votemetric.biometricchoice.modules.election;

import com.votemetric.biometricchoice.modules.candidate.Candidate;
import com.votemetric.biometricchoice.modules.electiondevices.ElectionDevice;
import com.votemetric.biometricchoice.modules.location.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
    private String description;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    private Boolean active;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    // Relationships
    @ManyToMany
    @JoinTable(
            name = "election_candidates",
            joinColumns = @JoinColumn(name = "election_id"),
            inverseJoinColumns = @JoinColumn(name = "candidate_id")
    )
    private List<Candidate> candidates;

    @OneToMany(mappedBy = "election")
    private List<ElectionDevice> electionDevices;

    public Election(Long electionId) {
        this.electionId = electionId;
    }
}
