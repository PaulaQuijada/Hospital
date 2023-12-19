package com.hospital.Repository;

import com.hospital.Domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, String> {
    boolean existsByDateScheduleAndTimeScheduleAndEmployeeNum(LocalDate dateSchedule, LocalTime timeSchedule, int employeeNum);
    boolean existsByDateScheduleBetweenAndTimeScheduleAndEmployeeNum(LocalDate startDateSchedule, LocalDate endDateSchedule, LocalTime timeSchedule, int employeeNum);

    boolean existsByDniAndDateScheduleAndTimeSchedule(String dni, LocalDate dateSchedule, LocalTime timeSchedule);
    List<Appointment> findByDni(String dni);
    List<Appointment> findByDniAndDateSchedule(String dni, LocalDate dateSchedule);
    List<Appointment> findByEmployeeNum(int employeeNum);
    Appointment findAppointmentByAppointmentNum(String appointmentNum);

}
