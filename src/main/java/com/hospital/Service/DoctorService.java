package com.hospital.Service;

import com.hospital.Controller.Inputs.DoctorInput;
import com.hospital.Controller.Updates.DoctorUpdate;
import com.hospital.Controller.Outputs.AppointmentOutput;
import com.hospital.Controller.Outputs.DoctorOutput;
import com.hospital.Domain.Appointment;
import com.hospital.Domain.Doctor;
import com.hospital.Exception.*;

import com.hospital.Repository.AppointmentRepository;
import com.hospital.Repository.DoctorRepository;
import com.hospital.Repository.NurseRepository;
import com.hospital.Repository.PatientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.*;


@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private HealthStaffService healthStaffService;

    public DoctorOutput addDoctor(DoctorInput doctorInput) throws DoctorAlreadyExistsException, NurseAlreadyExistsException, PatientAlreadyExistsException, DniAlreadyExistsException {
       boolean doctorDniExists = healthStaffService.validateDni(doctorInput.getDni());

        if (doctorDniExists) {
            throw new DniAlreadyExistsException("Dni already exists");
        } else {
            Doctor d = Doctor.getDoctorInput(doctorInput);
            doctorRepository.save(d);
            return DoctorOutput.getDoctor(d);

        }
    }

    public List<DoctorOutput> getAllDoctors() throws EmptyListException {
        List<Doctor> doctors = doctorRepository.findAll();
        List<DoctorOutput> doctorsOutput = new ArrayList<>();

        if (doctors.isEmpty()) {
            throw new EmptyListException("No doctors available");
        } else {
            for (Doctor doctor : doctors) {
                DoctorOutput doctorOutput = DoctorOutput.getDoctor(doctor);
                doctorsOutput.add(doctorOutput);
            }
        }

        return doctorsOutput;
    }

    public DoctorOutput getDoctorByEmployeeNum(int employeeNum) throws DoctorNotFoundException {
        boolean doctorExistsById = doctorRepository.existsById(employeeNum);

        if (doctorExistsById) {
            Doctor doctor = doctorRepository.findById(employeeNum);
            DoctorOutput doctorOutput = DoctorOutput.getDoctor(doctor);
            return doctorOutput;
        } else throw new DoctorNotFoundException("Doctor with ID: " + employeeNum + " not found");
    }

    public void updateSchedule(LocalTime start, LocalTime end, int employeeNum) throws DoctorNotFoundException {
        boolean doctorExistsById = doctorRepository.existsById(employeeNum);

        if (doctorExistsById) {
            Doctor doctor = doctorRepository.findById(employeeNum);
            doctor.setStartSchedule(start);
            doctor.setEndSchedule(end);
            doctorRepository.save(doctor);
        } else throw new DoctorNotFoundException("Doctor with ID: " + employeeNum + " not found");
    }

    public TreeMap<String, List<AppointmentOutput>> getBusiestDoctors() throws EmptyListException {
        // I locate the doctors and create a new TreeMap to add them later.
        List<Doctor> allDoctors = doctorRepository.findAll();
        TreeMap<String, List<AppointmentOutput>> busiestDoctor = new TreeMap<>(Collections.reverseOrder());

        // I scroll down the list of doctors to pass them to Output
        for (Doctor doctor : allDoctors) {
            String doctorId = doctor.getEmployeeNum();
            DoctorOutput doctorOutput = DoctorOutput.getDoctor(doctor);
            String doctorName = doctorOutput.getName(); // I am only interested in printing the name of the doctor.
            // I locate a doctor's appointments and create a new arraylist for them.
            List<Appointment> allAppointments = appointmentRepository.findByEmployeeNum(Integer.parseInt(doctorId));
            List<AppointmentOutput> allAppointmentOutput = new ArrayList<>();

            // I scroll through the list of appointments and we pass them to Output.
            for (Appointment appointment : allAppointments) {
                AppointmentOutput appointmentOutput = AppointmentOutput.getAppointment(appointment);
                allAppointmentOutput.add(appointmentOutput);
            }
            // This line sorts the appointments in ascending order according to the time of the appointment.
            allAppointmentOutput.sort(Comparator.comparing(AppointmentOutput::getTimeSchedule));

            // I add the doctors and their appointments to the TreeMap I create at the beginning of the method.
            busiestDoctor.put(doctorName, allAppointmentOutput);
        }

        if (busiestDoctor.isEmpty()) {
            throw new EmptyListException("Empty list");
        } else {
            return busiestDoctor;
        }
    }

    public void updateDoctor(int employeeNum, DoctorUpdate doctorUpdate) throws DoctorNotFoundException {
        boolean doctorExistsById = doctorRepository.existsById(employeeNum);

        if (doctorExistsById) {
            Doctor doctor = doctorRepository.findById(employeeNum);
            doctor.setName(doctorUpdate.getName());
            doctor.setAddress(doctorUpdate.getAddress());
            doctorRepository.save(doctor);
        } else throw new DoctorNotFoundException("Doctor not found");
    }

    public void deleteDoctor(int employeeNum) throws DoctorNotFoundException {
        boolean doctorExistsById = doctorRepository.existsById(employeeNum);

        if (doctorExistsById) {
            Doctor doctor = doctorRepository.findById(employeeNum);
            doctorRepository.delete(doctor);
        } else throw new DoctorNotFoundException("Doctor not found");
    }

    public TreeMap<LocalDate, List<LocalTime>> getSchedule(int id) throws EmployeeIdNotFoundException {
        Doctor doctor = doctorRepository.findById(id);
        if (doctor != null)
            return healthStaffService.getDateAvailable(doctor.getStartSchedule(), doctor.getEndSchedule(), id);
        else throw new EmployeeIdNotFoundException("Id not found");
    }

}
