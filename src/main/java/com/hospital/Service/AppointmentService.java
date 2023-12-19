package com.hospital.Service;

import com.hospital.Controller.Outputs.AppointmentOutput;
import com.hospital.Domain.Doctor;
import com.hospital.Domain.Nurse;
import com.hospital.Exception.*;
import com.hospital.Controller.Inputs.AppointmentInput;
import com.hospital.Domain.Appointment;
import com.hospital.Repository.AppointmentRepository;
import com.hospital.Repository.DoctorRepository;
import com.hospital.Repository.NurseRepository;
import com.hospital.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@Service
public class AppointmentService {

    private AppointmentRepository appointmentRepository;

    private PatientRepository patientRepository;

    private DoctorRepository doctorRepository;

    private NurseRepository nurseRepository;

    private HealthStaffService healthStaffService;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, PatientRepository patientRepository, DoctorRepository doctorRepository, NurseRepository nurseRepository, HealthStaffService healthStaffService) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.nurseRepository = nurseRepository;
        this.healthStaffService = healthStaffService;
    }

    public AppointmentOutput addAppointment(AppointmentInput a) throws DateNotAvailableException, AlreadyExistsException, PatientNotFoundException, EmployeeIdNotFoundException {
        validateAppointment(a);
        Appointment appointment = Appointment.getAppointment(a);
        appointmentRepository.save(appointment);
        return AppointmentOutput.getAppointment(appointment);
    }

    public List<AppointmentOutput> getAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        List<AppointmentOutput> appointmentOutputs = new ArrayList<>();
        for (Appointment appointment : appointments) {
            AppointmentOutput a = AppointmentOutput.getAppointment(appointment);
            appointmentOutputs.add(a);
        }
        return appointmentOutputs;
    }

    // To validate that the quotation meets the requirements to create it
    private boolean validateAppointment(AppointmentInput appointment) throws EmployeeIdNotFoundException, DateNotAvailableException, PatientNotFoundException {
        boolean patientExistsById = patientRepository.existsById(appointment.getDni());
        boolean appointmentExistsByDateAndTime = appointmentRepository.existsByDniAndDateScheduleAndTimeSchedule(appointment.getDni(), appointment.getDateSchedule(), appointment.getTimeSchedule());
        boolean appointmentExistById = appointmentRepository.existsById(appointment.getAppointmentNum());
        boolean appointmentExistsByDateAndTimeAndEmployeeNum = appointmentRepository.existsByDateScheduleAndTimeScheduleAndEmployeeNum(appointment.getDateSchedule(), appointment.getTimeSchedule(), appointment.getEmployeeNum());

        if (!validateHours(appointment)) throw new DateNotAvailableException("Date/time is not available");
        if (!patientExistsById) throw new PatientNotFoundException("Patient not found");
        if (appointmentExistsByDateAndTime)
            throw new AlreadyExistsException("Patient already has an appointment with this date/time");
        if (appointmentExistById) throw new AlreadyExistsException("Appointment id already exists");
        if (appointmentExistsByDateAndTimeAndEmployeeNum)
            throw new DateNotAvailableException("Date/time and employee num. are not available");
        return true;

    }


    // Validate that the hours are correct for a professional's schedule.

    private boolean validateHours(AppointmentInput appointment) throws EmployeeIdNotFoundException {
        boolean doctorExistsById = doctorRepository.existsById(appointment.getEmployeeNum());
        boolean nurseExistsById = nurseRepository.existsById(appointment.getEmployeeNum());

        TreeMap<LocalDate, List<LocalTime>> schedule;
        if (doctorExistsById) {
            Doctor doctor = doctorRepository.findById(appointment.getEmployeeNum());
            schedule = healthStaffService.getDateAvailable(doctor.getStartSchedule(), doctor.getEndSchedule(), Integer.parseInt(doctor.getEmployeeNum()));
        } else if (nurseExistsById) {
            Nurse nurse = nurseRepository.findById(appointment.getEmployeeNum());
            schedule = healthStaffService.getDateAvailable(nurse.getStartSchedule(), nurse.getEndSchedule(), Integer.parseInt(nurse.getEmployeeNum()));
        } else throw new EmployeeIdNotFoundException("Id not found");
        if (schedule.containsKey(appointment.getDateSchedule())) { // I check if the date of my appointment matches the doctor's available date.
            List<LocalTime> hours = schedule.get(appointment.getDateSchedule()); // I keep the times of the date available
            for (LocalTime hour : hours) {
                if (appointment.getTimeSchedule().equals(hour)) { // Check if the available hours match the time of the appointment.
                    return true;
                }
            }
            return false; // Return false on not finding the available time.
        } else return false;
    }


    public void deleteAppointment(int appointmentNum) throws DoctorNotFoundException, AppointmentNotFoundException {
        boolean appointmentExistsById = appointmentRepository.existsById(String.valueOf(appointmentNum));
        if (appointmentExistsById) {
            Appointment appointment = appointmentRepository.findAppointmentByAppointmentNum(String.valueOf(appointmentNum));
            appointmentRepository.delete(appointment);
        } else throw new AppointmentNotFoundException("Appointment not found");
    }
}
