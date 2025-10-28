package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.dto.validators.CreatePatientValidationGroup;
import com.pm.patientservice.service.PatientService;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    // the valid tag checks the valid tag and if there are any invalidities, spring goes and checks for erros
    // RequestBody tag converts the json request to our patient request DTO for us
    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(
            // what this does is that checks if the registeredDate is given during the create process
            @Validated({Default.class, CreatePatientValidationGroup.class})
            @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO); // this will include the latest info from the db
    }

    // when a client sends a put request to a URL like patients/SOME_UNIQUE_ID_STRING, Springs @PutMapping ("/{id}")
    // tells it to id that it is a PUT request for the patients path, grab whatever comes after the /patients/ cause
    // this is the {id} part and then it automatically tries to turn that string into a UUID object which is then passed
    // as the id parameter in the controller method
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO>

    //  @Validated({Default.class}) - tells spring to validate the class as per the defaults in the class
    // registeredDate can be ignored here, that's why it the validation group is not specified here
    updatePatient(@PathVariable UUID id, @Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
        // patientResponseDTO is going to be assigned the response on the call of the updatePatient method{
            PatientResponseDTO patientResponseDTO = patientService.updatePatient(id, patientRequestDTO);

            return ResponseEntity.ok().body(patientResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
