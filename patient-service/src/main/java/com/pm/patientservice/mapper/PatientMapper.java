package com.pm.patientservice.mapper;

import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.model.Patient;

public class PatientMapper {
    public static PatientResponseDTO toDTO(Patient patient) {
        PatientResponseDTO patientdto = new PatientResponseDTO();
        patientdto.setId(patient.getId().toString());
        patientdto.setName(patient.getName());
        patientdto.setAddress(patient.getAddress());
        patientdto.setEmail(patient.getEmail());
        patientdto.setId(patient.getDateOfBirth().toString());

        return patientdto;
    }
}
