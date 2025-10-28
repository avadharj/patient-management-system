package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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

    // function to update patient details
    // achieves this by taking in the id associated with that specific patient
    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        // this searches the repository for the id passed in the input
        // initially in case the id was not found in the repository, a null was returned, but now we have provided an
        // exception instead
        Patient patient = patientRepository.findById(id).orElseThrow(()
                -> new PatientNotFoundException("Patient not found with ID: " + id));

        if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistsException("A patient with this email already exists" +
                    patientRequestDTO.getEmail());
        }
        // now we need to ensure that each of the fields are updated which can do using the setters provided
        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient); // JPA handles the update for us
        // it returns the updated patient object and assigns it to the updated patient object given here
        return PatientMapper.toDTO(updatedPatient);
    }

    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }
    // we just need to take an id and delete the corresponding entry and nothing needs to be returned
}
