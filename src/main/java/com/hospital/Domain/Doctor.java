package com.hospital.Domain;

import com.hospital.Controller.Inputs.DoctorInput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "doctors")
@DiscriminatorValue("doctors")
public class Doctor extends HealthStaff {

    @NotNull
    @Positive(message = "Experience years must be positive")
    private int expYears;

    public Doctor(String name, String dni, String address, LocalTime startSchedule, LocalTime endSchedule, int expYears) {
        super(name, dni, address, startSchedule, endSchedule);
        this.expYears = expYears;
    }


    public static Doctor getDoctorInput(DoctorInput doctorInput){
        return new Doctor(doctorInput.getName(), doctorInput.getDni(), doctorInput.getAddress(),doctorInput.getStartSchedule(), doctorInput.getEndSchedule(), doctorInput.getExpYears());
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "name='" + getName() + '\'' +
                ", dni='" + getDni() + '\'' +
                ", address='" + getAddress() + '\'' +
                ", employeeNum='" + getEmployeeNum() + '\'' +
                ", startSchedule=" + getStartSchedule() +
                ", endSchedule=" + getEndSchedule() +
                ", expYears=" + expYears +
                '}';
    }
}
