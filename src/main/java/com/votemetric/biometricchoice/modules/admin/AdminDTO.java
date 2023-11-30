package com.votemetric.biometricchoice.modules.admin;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AdminDTO {
    private long id;
    @NotBlank(message = "username cannot be blank")
    @NonNull
    private String username;
    @NotBlank(message = "password cannot be blank")
    @NonNull
    private String password;

    private String role;


}