package com.hospital.Controller.Outputs;

import com.hospital.Domain.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatientOutput{
    @NotNull @NotBlank
    private String name;
    @NotNull
    @Pattern(regexp = "\\d{8}[A-Z]", message = "The DNI must contain 8 numbers followed by a capital letter")
    private String dni;
    @NotNull @NotBlank
    private String address;


    public static PatientOutput getPatient(Patient patient){
        return new PatientOutput(patient.getName(), patient.getAddress(), patient.getDni());
    }

}
