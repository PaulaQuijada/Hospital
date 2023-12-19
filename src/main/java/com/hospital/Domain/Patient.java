package com.hospital.Domain;

import com.hospital.Controller.Inputs.PatientInput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "patients")
public class Patient {
    @NotNull @NotBlank
    private String name;
    @Id
    @Pattern(regexp = "\\d{8}[A-Z]", message = "The DNI must contain 8 numbers followed by a capital letter")
    private String dni;
    @NotNull @NotBlank
    private String address;

    public static Patient getPatientInput(PatientInput patientInput){
        return new Patient(patientInput.getName(), patientInput.getDni(), patientInput.getAddress());
    }
}
