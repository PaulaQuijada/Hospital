package com.hospital.Controller.Outputs;

import com.hospital.Domain.Doctor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorOutput extends HealthStaffOutput {
    @NotNull
    @Positive(message = "Experience years must be positive")
    private int expYears;


    public DoctorOutput(String name, String employeeNum, LocalTime startSchedule, LocalTime endSchedule, int expYears) {
        super(name, employeeNum, startSchedule, endSchedule);
        this.expYears = expYears;
    }

    public static DoctorOutput getDoctor(Doctor doctor) {
        return new DoctorOutput(doctor.getName(), doctor.getEmployeeNum(), doctor.getStartSchedule(), doctor.getEndSchedule(), doctor.getExpYears());
    }
}
