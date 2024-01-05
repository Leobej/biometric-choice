package com.votemetric.biometricchoice.modules.electiondevices;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ElectionDeviceDTO {
    private long id;
    private long electionId;
    private long deviceId;
}