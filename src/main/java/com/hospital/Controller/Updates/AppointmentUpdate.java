package com.hospital.Controller.Updates;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentUpdate {
    @NotNull
    private LocalDate dateSchedule;
    @NotNull
    private LocalTime timeSchedule;


}
