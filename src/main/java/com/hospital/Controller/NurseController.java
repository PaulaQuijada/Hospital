package com.hospital.Controller;

import com.hospital.Controller.Inputs.NurseInput;
import com.hospital.Controller.Outputs.DoctorOutput;
import com.hospital.Controller.Updates.NurseUpdate;
import com.hospital.Controller.Outputs.NurseOutput;
import com.hospital.Exception.*;
import com.hospital.Service.HealthStaffService;
import com.hospital.Service.NurseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.TreeMap;

@RestController
@RequestMapping("/nurses")
@Slf4j
public class NurseController {
    @Autowired
    private NurseService nurseService;
    @Autowired
    private HealthStaffService healthStaffService;

    @PostMapping
    public NurseOutput addNurse(@Valid @RequestBody NurseInput n) throws NurseAlreadyExistsException, DoctorAlreadyExistsException, EmployeeNumAlreadyExistsException, PatientAlreadyExistsException {
        NurseOutput nurseOutput = nurseService.addNurse(n);
        log.info("Nurse save: {}", n);
        return nurseOutput;
    }

    @GetMapping
    public List<NurseOutput> getNurses() throws EmptyListException {
        List<NurseOutput> nurses = nurseService.getAllNurses();
        log.info("Doctors are being listed:");
        return nurses;
    }

    @GetMapping("/{employeeNum}")
    public NurseOutput getNurseByEmployeeNum(@PathVariable int employeeNum) throws NurseNotFoundException {
        NurseOutput nurseOutput = nurseService.getNurseByEmployeeNum(employeeNum);
        log.info("Doctor is being listed:");
        return nurseOutput;
    }
    @PutMapping("/{id}/schedule/{start}/{end}")
    public void updateSchedule(@Valid @PathVariable int id, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime start, @PathVariable @DateTimeFormat (iso = DateTimeFormat.ISO.TIME)LocalTime end) throws NurseNotFoundException {
        log.warn("Updating timetable for ID {} with start {} and end {}", id, start, end);
        nurseService.updateSchedule(start, end, id);
    }
    @GetMapping("/{id}/schedule")
    public TreeMap<LocalDate, List<LocalTime>> getSchedule(@Valid @PathVariable int id) throws EmployeeIdNotFoundException {
        log.info("Schedule is being listed: ");
        return nurseService.getSchedule(id);
    }

    @PutMapping("/{employeeNum}")
    public void updateDoctor(@Valid @PathVariable int employeeNum, @RequestBody NurseUpdate nurseUpdate) throws NurseNotFoundException {
        log.warn("Updating data for ID {}", employeeNum);
        nurseService.updateNurse(employeeNum, nurseUpdate);
    }
    @DeleteMapping("/{employeeNum}")
    public void deleteNurse(@PathVariable int employeeNum) throws NurseNotFoundException {
        nurseService.deleteNurse(employeeNum);
        log.warn("Nurse is being removed:");
    }
}
