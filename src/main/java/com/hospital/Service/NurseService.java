package com.hospital.Service;

import com.hospital.Controller.Inputs.NurseInput;
import com.hospital.Controller.Updates.NurseUpdate;
import com.hospital.Controller.Outputs.NurseOutput;
import com.hospital.Domain.Nurse;
import com.hospital.Exception.*;

import com.hospital.Repository.AppointmentRepository;
import com.hospital.Repository.DoctorRepository;
import com.hospital.Repository.NurseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@Service
public class NurseService {
    @Autowired
    private NurseRepository nurseRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private HealthStaffService healthStaffService;


    public NurseOutput addNurse(NurseInput nurseInput) throws AlreadyExistsException, NurseAlreadyExistsException, DoctorAlreadyExistsException, PatientAlreadyExistsException, EmployeeNumAlreadyExistsException {
        boolean nurseDniExists = healthStaffService.validateDni(nurseInput.getDni());

        if (nurseDniExists) {
            throw new AlreadyExistsException("Dni already exists");
        } else {
            Nurse n = Nurse.getNurse(nurseInput);
            nurseRepository.save(n);
            return NurseOutput.getNurse(n);
        }

    }

    //Obtener lista de todos los enfermeros
    public List<NurseOutput> getAllNurses() throws EmptyListException {
        List<Nurse> nurses = nurseRepository.findAll();
        List<NurseOutput> nursesOutput = new ArrayList<>();

        if (nurses.isEmpty()) {
            throw new EmptyListException("No nurses available");
        } else {
            for (Nurse nurse : nurses) {
                NurseOutput nurseOutput = NurseOutput.getNurse(nurse);
                nursesOutput.add(nurseOutput);
            }
        }

        return nursesOutput;
    }

    //Obtener un enfermero en concreto
    public NurseOutput getNurseByEmployeeNum(int employeeNum) throws NurseNotFoundException {
        boolean nurseExistsById = nurseRepository.existsById(employeeNum);

        if (nurseExistsById) {
            Nurse nurse = nurseRepository.findById(employeeNum);
            NurseOutput nurseOutput = NurseOutput.getNurse(nurse);
            return nurseOutput;
        } else throw new NurseNotFoundException("Nurse with ID: " + employeeNum + " not found");
    }

    //Actualizar el horario de un enfermero
    public void updateSchedule(LocalTime start, LocalTime end, int employeeNum) throws NurseNotFoundException {
        boolean nurseExistsById = nurseRepository.existsById(employeeNum);

        if (nurseExistsById) {
            Nurse nurse = nurseRepository.findById(employeeNum);
            nurse.setStartSchedule(start);
            nurse.setEndSchedule(end);
            nurseRepository.save(nurse);
        } else throw new NurseNotFoundException("Nurse with ID: " + employeeNum + " not found");
    }

    public void updateNurse(int employeeNum, NurseUpdate nurseUpdate) throws NurseNotFoundException {
        boolean nurseExistsById = nurseRepository.existsById(employeeNum);

        if (nurseExistsById) {
            Nurse nurse = nurseRepository.findById(employeeNum);
            nurse.setName(nurseUpdate.getName());
            nurse.setAddress(nurseUpdate.getAddress());
            nurseRepository.save(nurse);
        } else throw new NurseNotFoundException("Nurse not found");
    }

    public void deleteNurse(int employeeNum) throws NurseNotFoundException {
        boolean nurseExistsById = nurseRepository.existsById(employeeNum);

        if (nurseExistsById) {
            Nurse nurse = nurseRepository.findById(employeeNum);
            nurseRepository.delete(nurse);
        } else throw new NurseNotFoundException("Nurse not found");
    }

    public TreeMap<LocalDate, List<LocalTime>> getSchedule(int id) throws EmployeeIdNotFoundException {
        Nurse nurse = nurseRepository.findById(id);
        if (nurse != null)
            return healthStaffService.getDateAvailable(nurse.getStartSchedule(), nurse.getEndSchedule(),id);
        else throw new EmployeeIdNotFoundException("Id not found");
    }
}
