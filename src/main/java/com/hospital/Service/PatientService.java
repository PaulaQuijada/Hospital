package com.hospital.Service;


import com.hospital.Controller.Inputs.PatientInput;
import com.hospital.Controller.Outputs.DoctorOutput;
import com.hospital.Controller.Updates.PatientUpdate;
import com.hospital.Controller.Outputs.AppointmentOutput;
import com.hospital.Controller.Outputs.PatientOutput;
import com.hospital.Domain.Appointment;
import com.hospital.Domain.Doctor;
import com.hospital.Domain.Patient;
import com.hospital.Exception.*;
import com.hospital.Repository.AppointmentRepository;
import com.hospital.Repository.DoctorRepository;
import com.hospital.Repository.NurseRepository;
import com.hospital.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private NurseRepository nurseRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    public PatientOutput addPatient(PatientInput patientInput) throws AlreadyExistsException, NurseAlreadyExistsException, DoctorAlreadyExistsException, PatientAlreadyExistsException {
       boolean dniExists = patientRepository.existsById(patientInput.getDni()) || doctorRepository.existsByDni(patientInput.getDni()) || nurseRepository.existsByDni(patientInput.getDni());

        if (dniExists) {
            throw new AlreadyExistsException("The dni you are trying to add already exists");
        } else {
            Patient p = Patient.getPatientInput(patientInput);
            patientRepository.save(p);
            return PatientOutput.getPatient(p);
        }
    }

    public List<PatientOutput> getAllPatients() throws EmptyListException {
        List<Patient> patients = patientRepository.findAll();
        List<PatientOutput> patientsOutput = new ArrayList<>();

        if (patients.isEmpty()) {
            throw new EmptyListException("No patients available");
        } else {
            for (Patient patient : patients) {
                PatientOutput patientOutput = PatientOutput.getPatient(patient);
                patientsOutput.add(patientOutput);
            }
        }

        return patientsOutput;
    }

    //Obtener las citas del paciente de un día en concreto
    public List<AppointmentOutput> getAppointmentByDate(String dni, LocalDate date) throws PatientNotFoundException {
        boolean patientExistsById = patientRepository.existsById(dni);

        if (patientExistsById) {
            List<Appointment> patientAppointments = appointmentRepository.findByDniAndDateSchedule(dni, date);
            List<AppointmentOutput> appointments = new ArrayList<>();
            for (Appointment appointment : patientAppointments) {
                AppointmentOutput appointmentOutput = AppointmentOutput.getAppointment(appointment);
                appointments.add(appointmentOutput);

            }
            //Para ordenar las citas utilizo esta expresión lambda:
            appointments.sort((a, b) -> a.getTimeSchedule().compareTo(b.getTimeSchedule()));
            return appointments;
        } else throw new PatientNotFoundException("Patient not found");
    }

    //Devuelve todas las citas de un paciente
    public TreeMap<LocalDate, List<AppointmentOutput>> getAppointments(String dni) throws PatientNotFoundException {
        boolean patientExistsById = patientRepository.existsById(dni);

        if (!patientExistsById) {
            throw new PatientNotFoundException("Patient not found");
        } else {
            TreeMap<LocalDate, List<AppointmentOutput>> treeMap = new TreeMap<>();
            List<Appointment> appointments = appointmentRepository.findByDni(dni);
            List<AppointmentOutput> appointmentsOutput = new ArrayList<>();
            for (Appointment a : appointments) {
                AppointmentOutput appointmentOutput = AppointmentOutput.getAppointment(a);
                appointmentsOutput.add(appointmentOutput);
            }
            if (appointmentsOutput != null) {
                for (AppointmentOutput appointment : appointmentsOutput) {
                    List<AppointmentOutput> appointmentsForDate = treeMap.get(appointment.getDateSchedule());

                    if (appointmentsForDate == null) {
                        // Si no existe, crea una nueva lista
                        appointmentsForDate = new ArrayList<>();
                        treeMap.put(appointment.getDateSchedule(), appointmentsForDate);
                    }

                    // Agrega la cita a la lista correspondiente a la fecha
                    appointmentsForDate.add(appointment);

                }
                for (List<AppointmentOutput> appointmentsForDate : treeMap.values()) {
                    appointmentsForDate.sort(Comparator.comparing(AppointmentOutput::getTimeSchedule));
                }

            }
            return treeMap;
        }
    }

    public void updatePatient(String dni, PatientUpdate patientUpdate) throws PatientNotFoundException {
        boolean patientExistsById = patientRepository.existsById(dni);

        if(patientExistsById){
            Patient patient = patientRepository.findByDni(dni);
            patient.setName(patientUpdate.getName());
            patient.setAddress(patientUpdate.getAddress());
            patientRepository.save(patient);
        } else throw new PatientNotFoundException("Patient not found");
    }
    public void deletePatient(String dni) throws PatientNotFoundException{
        boolean patientExistsById = patientRepository.existsById(dni);

        if(patientExistsById){
            Patient patient = patientRepository.findByDni(dni);
            patientRepository.delete(patient);
        } else throw new PatientNotFoundException("Patient not found");
    }
}
