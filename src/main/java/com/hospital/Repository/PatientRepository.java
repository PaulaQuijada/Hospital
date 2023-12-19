package com.hospital.Repository;

import com.hospital.Domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {
    boolean existsById(String dni);
    Patient findByDni(String dni);

}
