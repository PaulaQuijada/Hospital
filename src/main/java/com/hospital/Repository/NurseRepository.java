package com.hospital.Repository;

import com.hospital.Domain.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Integer> {
    boolean existsByDni(String dni);
    boolean existsById(int employeeNum);
    Nurse findById(int employeeNum);

}
