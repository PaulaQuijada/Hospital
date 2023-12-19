package com.hospital.Service;

import com.hospital.Exception.*;
import com.hospital.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;


// SERVICE WITH COMMON METHODS FOR DOCTOR AND NURSE
@Service
public class HealthStaffService {
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private NurseRepository nurseRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    // I take out the DAILY timetable of a professional
    private List<LocalTime> getSchedule(LocalTime start, LocalTime end) throws DoctorNotFoundException {
        List<LocalTime> schedule = new ArrayList<>();
        while (start.isBefore(end)) {
            schedule.add(start);
            start = start.plusMinutes(30);
        }
        return schedule;
    }

    // I take out the WEEKLY timetable of a professional
    private TreeMap<LocalDate, List<LocalTime>> getWeekSchedule(LocalTime start, LocalTime end) {
        List<LocalTime> hours = getSchedule(start, end);
        LocalDate today = LocalDate.now();
        LocalDate nextMonday = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        TreeMap<LocalDate, List<LocalTime>> weekSchedule = new TreeMap<>();
        for (LocalDate i = nextMonday; i.isBefore(nextMonday.plusDays(5)); i = i.plusDays(1)) {
            weekSchedule.put(i, hours);
        }
        return weekSchedule;
    }

    // I take out the available hours of a professional
    public TreeMap<LocalDate, List<LocalTime>> getDateAvailable(LocalTime start, LocalTime end, int employeeNum) {
        TreeMap<LocalDate, List<LocalTime>> schedule = getWeekSchedule(start, end);
        TreeMap<LocalDate, List<LocalTime>> newSchedule = new TreeMap<>();
        LocalDate today = LocalDate.now();
        LocalDate nextMonday = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        LocalDate nextFriday = nextMonday.plusDays(5);

        for (LocalDate i = nextMonday; i.isBefore(nextFriday); i = i.plusDays(1)) {
            List<LocalTime> hours = schedule.get(i);
            List<LocalTime> newHours = new ArrayList<>();

            // Define the start and end dates of the range for the BETWEEN query.
            LocalDate startDate = i; // Start date of the range
            LocalDate endDate = i;   // Range end date (can be the same as startDate if you are looking for a specific date)

            for (LocalTime time : hours) {
                if (!appointmentRepository.existsByDateScheduleBetweenAndTimeScheduleAndEmployeeNum(startDate, endDate, time, employeeNum)) {
                    newHours.add(time);
                }
            }
            newSchedule.put(i, newHours);
        }
        return newSchedule;
    }



    // Method to validate if the dni already exists
    public boolean validateDni(String dni) throws DoctorAlreadyExistsException, NurseAlreadyExistsException, PatientAlreadyExistsException {
        if (doctorRepository.existsByDni(dni)) {
            throw new DoctorAlreadyExistsException("Doctor already exists");
        } else if (nurseRepository.existsByDni(dni)) {
            throw new NurseAlreadyExistsException("Nurse already exists");
        } else if (patientRepository.existsById(dni)) {
            throw new PatientAlreadyExistsException("Patient already exists");
        } else return false;
    }
}
