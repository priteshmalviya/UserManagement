package com.pritesh.usermanagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpUserDto {

    @NotNull
    private String name;

    @NotNull
    private String userName;

    @NotNull
    private String email;

    @NotNull
    private String password;
}
