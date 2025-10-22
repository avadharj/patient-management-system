package com.pm.patientservice.mapper;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.model.Patient;

import java.time.LocalDate;

public class PatientMapper {
    public static PatientResponseDTO toDTO(Patient patient) {
        PatientResponseDTO patientdto = new PatientResponseDTO();
        patientdto.setId(patient.getId().toString());
        patientdto.setName(patient.getName());
        patientdto.setAddress(patient.getAddress());
        patientdto.setEmail(patient.getEmail());
        patientdto.setDateOfBirth(patient.getDateOfBirth().toString());

        return patientdto;
    }
    public static Patient toModel(PatientRequestDTO patientRequestDTO) {
        Patient patient = new Patient(); // new empty patient object

        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        patient.setRegisteredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate()));
        return patient;
    }
}
