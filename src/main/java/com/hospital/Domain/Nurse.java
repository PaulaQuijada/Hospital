package com.hospital.Domain;

import com.hospital.Controller.Inputs.NurseInput;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table (name = "nurses")
@DiscriminatorValue("nurses")
public class Nurse extends HealthStaff{


    public Nurse(String name, String dni, String address, LocalTime startSchedule, LocalTime endSchedule) {
        super(name, dni, address, startSchedule, endSchedule);
    }

    public Nurse() {
    }

    public static Nurse getNurse(NurseInput nurseInput){
        return new Nurse(nurseInput.getName(), nurseInput.getDni(), nurseInput.getAddress(), nurseInput.getStartSchedule(), nurseInput.getEndSchedule());
    }
}
