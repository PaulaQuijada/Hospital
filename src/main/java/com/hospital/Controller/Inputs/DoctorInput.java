package com.hospital.Controller.Inputs;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.time.LocalTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorInput extends HealthStaffInput {
    @NotNull
    @Positive(message = "Experience years must be positive")
    private int expYears;


}
