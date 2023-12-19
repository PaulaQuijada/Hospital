package com.hospital.Domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass // Tables are created from the child classes but not from the parent class.
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Create table without inheritance
@DiscriminatorValue("healthstaff")
public abstract class HealthStaff {
    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    @NotNull
    @Pattern(regexp = "\\d{8}[A-Z]", message = "The DNI must contain 8 numbers followed by a capital letter")
    private String dni;
    @NotBlank
    @NotNull
    private String address;
    @Id
    @NotNull
    private String employeeNum;
    @NotNull
    private LocalTime startSchedule;
    @NotNull
    private LocalTime endSchedule;


    public HealthStaff(String name, String dni, String address, LocalTime startSchedule, LocalTime endSchedule) {
        this.name = name;
        this.dni = dni;
        this.address = address;
        this.employeeNum = UUID.randomUUID().toString();
        this.startSchedule = startSchedule;
        this.endSchedule = endSchedule;
    }

}
