package com.hospital.Controller.Updates;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HealthStaffUpdate {
    @NotNull @NotBlank
    private String name;
    @NotNull @NotBlank
    private String address;
}
