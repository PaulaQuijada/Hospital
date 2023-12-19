package com.hospital.Controller.Inputs;


import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentInput {
    @NotNull
    private LocalDate dateSchedule;
    @NotNull
    private LocalTime timeSchedule;
    @Positive(message = "Employee number must be positive")
    private int employeeNum;
    @Pattern(regexp = "\\d{8}[A-Z]", message = "The DNI must contain 8 numbers followed by a capital letter")
    private String dni;
    @NotNull
    private String appointmentNum;
}
