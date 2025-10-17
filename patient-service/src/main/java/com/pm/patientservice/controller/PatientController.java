package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// telling spring that this is a controller and expects requests directed towards /patients
@RestController
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // @GetMapping - annotation used to map HTTP GET requests to specific handler methods within the controller
    // ResponseEntity is a Spring object that creates an HTTP response without us having to write the code ourselves
    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
        List<PatientResponseDTO> patients = patientService.getPatients();
        // Return a status code 200 back to the client meaning all went ok:
        return ResponseEntity.ok(patientService.getPatients());
    }
}
