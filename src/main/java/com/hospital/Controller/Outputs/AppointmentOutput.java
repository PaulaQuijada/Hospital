package com.hospital.Controller.Outputs;

import com.hospital.Domain.Appointment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class AppointmentOutput {
    @NotNull
    private LocalDate dateSchedule;
    @NotNull
    private LocalTime timeSchedule;
    @NotNull
    @Positive(message = "Employee number must be positive")
    private int employeeNum;
    @Pattern(regexp = "\\d{8}[A-Z]", message = "The DNI must contain 8 numbers followed by a capital letter")
    private String dni;
    @NotNull
    private String appointmentNum;

    public AppointmentOutput(LocalDate dateSchedule, LocalTime timeSchedule, int employeeNum, String dni, String appointmentNum) {
        this.dateSchedule = dateSchedule;
        this.timeSchedule = timeSchedule;
        this.employeeNum = employeeNum;
        this.dni = dni;
        this.appointmentNum = appointmentNum;
    }
    public static AppointmentOutput getAppointment(Appointment a){
        return new AppointmentOutput(a.getDateSchedule(),a.getTimeSchedule(),a.getEmployeeNum(),a.getDni(),a.getAppointmentNum());
    }
}
