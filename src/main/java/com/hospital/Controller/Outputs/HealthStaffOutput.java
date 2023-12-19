package com.hospital.Controller.Outputs;

import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public abstract class HealthStaffOutput {
    @NotNull @NotBlank
    private String name;

    @NotNull
    private String employeeNum;

    private LocalTime startSchedule;
    private LocalTime endSchedule;

    public HealthStaffOutput(String name, String employeeNum, LocalTime startSchedule, LocalTime endSchedule) {
        this.name = name;
        this.employeeNum = employeeNum;
        this.startSchedule = startSchedule;
        this.endSchedule = endSchedule;
    }
}
