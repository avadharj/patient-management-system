package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// this class is where all the business logic and DTO conversion happens for a given request
@Service
public class PatientService {

    private PatientRepository  patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll(); // returns a list of all the patient domain entity models

        // we created a new list of Patient Response DTOs and set it to the RHS
        // the RHS is similar to a for loop i.e it iterates over each element in the for loop and then we get the
        // patient list
        return patients.stream().map(patient -> PatientMapper.toDTO(patient)).toList();
    }
}
