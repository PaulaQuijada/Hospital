package com.hospital.Controller.Inputs;

import com.hospital.Domain.Nurse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalTime;


public class NurseInput extends HealthStaffInput {

    public NurseInput(@NotNull @NotBlank String name, @NotBlank @Pattern(regexp = "\\d{8}[A-Z]", message = "The DNI must contain 8 numbers followed by a capital letter") String dni, @NotNull @NotBlank String address, LocalTime startSchedule, LocalTime endSchedule) {
        super(name, dni, address, startSchedule, endSchedule);
    }
    public NurseInput() {
    }

    public static NurseInput getNurse(Nurse nurse){
        return new NurseInput(nurse.getName(), nurse.getDni(), nurse.getAddress(), nurse.getStartSchedule(), nurse.getEndSchedule());
    }
}
