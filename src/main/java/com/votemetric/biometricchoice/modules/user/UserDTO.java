package com.votemetric.biometricchoice.modules.user;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String email;
    private String password; // Ensure secure handling


}
