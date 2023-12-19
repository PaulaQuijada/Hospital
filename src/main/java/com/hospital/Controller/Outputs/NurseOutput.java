package com.hospital.Controller.Outputs;


import com.hospital.Domain.Nurse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class NurseOutput extends HealthStaffOutput {

    public NurseOutput(String name, String employeeNum, LocalTime startSchedule, LocalTime endSchedule) {
        super(name,employeeNum, startSchedule, endSchedule);
    }

    public static NurseOutput getNurse(Nurse nurse) {
        return new NurseOutput(nurse.getName(), nurse.getEmployeeNum(), nurse.getStartSchedule(), nurse.getEndSchedule());
    }
}
