package com.pm.patientservice.dto;

// why do we need a DTO:
// in the entity, we are using more complicated datatypes that may not be ideal when working with HTTP
// responses which expect json types
// The conversion of those datatypes to a format that can be transported using json is hard
// DTO abstracts those complexities away by converting all of them into a more palettable format for json

public class PatientResponseDTO {
    private String id;
    private String name;
    private String email;
    private String address;
    private String dateOfBirth;

    // we are not adding a field for registeredDate in this cause that field is only for auditing reasons
    // it need not be in the DTO response

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
