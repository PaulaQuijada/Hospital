package com.hospital.Domain;

import com.hospital.Controller.Inputs.AppointmentInput;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "appointments")
public class Appointment {
    @NotNull
    private LocalDate dateSchedule;
    @NotNull

    private LocalTime timeSchedule;
    @NotNull
    @Positive(message = "Employee number must be positive")
    private int employeeNum;
    @NotBlank @NotNull
    @Pattern(regexp = "\\d{8}[A-Z]", message = "The DNI must contain 8 numbers followed by a capital letter")
    private String dni;
    @Id
    private String appointmentNum;

    public Appointment(LocalDate dateSchedule, LocalTime timeSchedule, int employeeNum, String dni) {
        this.dateSchedule = dateSchedule;
        this.timeSchedule = timeSchedule;
        this.employeeNum = employeeNum;
        this.dni = dni;
        this.appointmentNum = UUID.randomUUID().toString();
    }

    public static Appointment getAppointment(AppointmentInput a){
        return new Appointment(a.getDateSchedule(), a.getTimeSchedule(), a.getEmployeeNum(), a.getDni());
    }
}
