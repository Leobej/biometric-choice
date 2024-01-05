package com.votemetric.biometricchoice.modules.electiondevices;

import com.votemetric.biometricchoice.modules.device.Device;
import com.votemetric.biometricchoice.modules.election.Election;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "election_devices")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ElectionDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "election_id", nullable = false)
    private Election election;

    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

}
