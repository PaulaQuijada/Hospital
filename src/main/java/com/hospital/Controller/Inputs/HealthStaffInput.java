package com.hospital.Controller.Inputs;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public abstract class HealthStaffInput {
    @NotNull
    @NotBlank
    private String name;
    @NotBlank
    @NotNull
    @Pattern(regexp = "\\d{8}[A-Z]", message = "The DNI must contain 8 numbers followed by a capital letter")
    private String dni;
    @NotNull
    @NotBlank
    private String address;
    @NotNull
    private LocalTime startSchedule;
    @NotNull
    private LocalTime endSchedule;


    public HealthStaffInput(String name, String dni, String address, LocalTime startSchedule, LocalTime endSchedule) {
        this.name = name;
        this.dni = dni;
        this.address = address;
        this.startSchedule = startSchedule;
        this.endSchedule = endSchedule;
    }
}
