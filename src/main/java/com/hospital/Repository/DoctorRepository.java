package com.hospital.Repository;

import com.hospital.Domain.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    boolean existsByDni(String dni);
    boolean existsById(int employeeNum);
    Doctor findById(int employeeNum);
}
