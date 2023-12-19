package com.hospital.Controller;

import com.hospital.Controller.Inputs.DoctorInput;
import com.hospital.Controller.Updates.DoctorUpdate;
import com.hospital.Controller.Outputs.AppointmentOutput;
import com.hospital.Controller.Outputs.DoctorOutput;
import com.hospital.Exception.*;
import com.hospital.Service.DoctorService;
import com.hospital.Service.HealthStaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.TreeMap;

@RestController
@Slf4j
@RequestMapping("/doctors")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private HealthStaffService healthStaffService;

    @PostMapping
    public DoctorOutput addDoctor(@Valid @RequestBody DoctorInput d) throws NurseAlreadyExistsException, DoctorAlreadyExistsException, EmployeeNumAlreadyExistsException, PatientAlreadyExistsException, DniAlreadyExistsException {
        DoctorOutput doctorOutput = doctorService.addDoctor(d);
        log.info("Doctor save: {}", doctorOutput);
        return doctorOutput;
    }

    @GetMapping
    public List<DoctorOutput> getDoctors() throws EmptyListException {

        List<DoctorOutput> doctors = doctorService.getAllDoctors();
        log.info("Doctors are being listed:");
        return doctors;

    }

    @GetMapping("/{employeeNum}")
    public DoctorOutput getDoctorByEmployeeNum(@PathVariable int employeeNum) {

        DoctorOutput doctorOutput = doctorService.getDoctorByEmployeeNum(employeeNum);
        log.info("Doctor is being listed:");
        return doctorOutput;
    }

    @PutMapping("/{id}/schedule/{start}/{end}")
    public void updateSchedule(@Valid @PathVariable int id, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime start, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime end) {
        log.warn("Updating timetable for ID {} with start {} and end {}", id, start, end);
        doctorService.updateSchedule(start, end, id);


    }

    @GetMapping("/{id}/schedule")
    public TreeMap<LocalDate, List<LocalTime>> getSchedule(@Valid @PathVariable int id) throws EmployeeIdNotFoundException {
        log.info("Schedule is being listed: ");
        return doctorService.getSchedule(id);

    }

    @GetMapping("/getBusiests")
    public TreeMap<String, List<AppointmentOutput>> getDoctorsWithMostAppointments() throws EmptyListException {
        log.info("Busiests doctors is being listed:");
        return doctorService.getBusiestDoctors();

    }

    @PutMapping("/{employeeNum}")
    public void updateDoctor(@Valid @PathVariable int employeeNum, @RequestBody DoctorUpdate doctorUpdate) {
        log.warn("Updating data for ID {}", employeeNum);
            doctorService.updateDoctor(employeeNum, doctorUpdate);

    }

    @DeleteMapping("/{employeeNum}")
    public void deleteDoctor(@Valid @PathVariable int employeeNum) {

            doctorService.deleteDoctor(employeeNum);
            log.warn("Doctor is being removed:");

    }
}

