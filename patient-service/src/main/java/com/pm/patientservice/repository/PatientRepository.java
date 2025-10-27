package com.pm.patientservice.repository;

import com.pm.patientservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

// adding the repository tag to indicate that this is a JPA repository ,and it has the input parameters of the Patient
// entity and the type of the ID used to identify it
// extending JPA repository gives us access to a lot of out-of-the-box CRUD functionalities
@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    boolean existsByEmail(String email);
}
