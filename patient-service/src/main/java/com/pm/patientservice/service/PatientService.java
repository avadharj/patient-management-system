package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
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

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("A patient with this email already exists" +
                    patientRequestDTO.getEmail());
        }
        // having an exception here means that if the email already exists, then an error is thrown and the rest of the
        // code is not going to run
        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
        // we cant save the patientRequestDTO to the repository as it is not compatible
        // need to convert it to a format that is compatible using the Patient Mapper
        // that is why we pass in the result of applying PatientMapper's toModel on to the request DTO object
        // finally, behind the scenes it is this that gets persisted to the db

        // we can convert the newPatient formed back to a DTO and return that to the controller
        return PatientMapper.toDTO(newPatient);
    }
}
