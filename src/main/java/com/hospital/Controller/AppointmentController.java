package com.hospital.Controller;

import com.hospital.Controller.Inputs.AppointmentInput;
import com.hospital.Controller.Outputs.AppointmentOutput;
import com.hospital.Domain.Appointment;
import com.hospital.Exception.*;
import com.hospital.Service.AppointmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/appointments")
@Slf4j
public class AppointmentController {

    private AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public AppointmentOutput addAppointment(@Valid @RequestBody AppointmentInput appointment) throws DateNotAvailableException, EmployeeNumAlreadyExistsException, EmployeeIdNotFoundException, NurseNotFoundException, PatientNotFoundException {

        AppointmentOutput appointmentOutput = appointmentService.addAppointment(appointment);
        log.info("Appointment save: {}", appointment);
        return appointmentOutput;
    }

    @GetMapping
    public List<AppointmentOutput> getAppointments() {
        List<AppointmentOutput> appointments = appointmentService.getAppointments();
        log.info("Appointments are being listed:");
        return appointments;
    }

    @DeleteMapping("/{appointmentNum}")
    public void deleteAppointment(@Valid @PathVariable int appointmentNum) throws AppointmentNotFoundException {

        appointmentService.deleteAppointment(appointmentNum);
           log.warn("Appointment deleted:");

    }
}
