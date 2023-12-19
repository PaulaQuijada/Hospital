package com.hospital.Controller;

import com.hospital.Controller.Outputs.NurseOutput;
import com.hospital.Controller.Updates.PatientUpdate;
import com.hospital.Controller.Outputs.AppointmentOutput;
import com.hospital.Controller.Inputs.PatientInput;
import com.hospital.Controller.Outputs.PatientOutput;
import com.hospital.Exception.*;
import com.hospital.Service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/patients")
@Slf4j
public class PatientController {
    @Autowired
    private PatientService patientService;
    @PostMapping
    public PatientOutput addPatient(@Valid @RequestBody PatientInput p) throws NurseAlreadyExistsException, DoctorAlreadyExistsException, PatientAlreadyExistsException {
        PatientOutput patientOutput = patientService.addPatient(p);
        log.info("Patient save: {}", p);
        return patientOutput;
    }

    @GetMapping
    public ResponseEntity<List<PatientOutput>> getPatients() {
        try {
            List<PatientOutput> patients = patientService.getAllPatients();
            if (patients.isEmpty()) return ResponseEntity.noContent().build();
            else return ResponseEntity.ok(patients);
        } catch (EmptyListException e) {
            System.out.println(e.getMessage());
            ResponseEntity.status(HttpStatus.CONFLICT).body("The list of patients is empty");
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{dni}/appointments/{date}")
    public ResponseEntity<List<AppointmentOutput>> getAppointmentList(@PathVariable String dni, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate date){
        try{
            return ResponseEntity.ok(patientService.getAppointmentByDate(dni,date));
        } catch (PatientNotFoundException e) {
            System.out.println(e.getMessage());
           return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{dni}")
    public ResponseEntity updateDoctor(@Valid @PathVariable String dni, @RequestBody PatientUpdate patientUpdate){
        try{
            patientService.updatePatient(dni, patientUpdate);
            return ResponseEntity.ok().body("Patient updated successfully");
        } catch (PatientNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{dni}")
    public ResponseEntity deleteDoctor(@PathVariable String dni){
        try{
            patientService.deletePatient(dni);
            return ResponseEntity.ok().body("Patient removed successfully");
        }
        catch (PatientNotFoundException e){
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
