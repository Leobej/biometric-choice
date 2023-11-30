package com.votemetric.biometricchoice.modules.location;

import com.votemetric.biometricchoice.modules.voterhistory.VoterHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "locations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long locationId;

    @Column(nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false, unique = true)
    private String street;

    @Column(nullable = false, unique = true)
    private String number;

    // Relationships
    @OneToMany(mappedBy = "location")
    private List<VoterHistory> voterHistories;
}

