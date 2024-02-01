package com.votemetric.biometricchoice.modules.device;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDTO {
    private Long id;
    private String type;
    private String status;
    private String name;
    private Long locationId;
}
