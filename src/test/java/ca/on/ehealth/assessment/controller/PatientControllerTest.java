package ca.on.ehealth.assessment.controller;

import ca.on.ehealth.assessment.AbstractIntegrationTest;
import ca.on.ehealth.assessment.exception.ErrorMessage;
import ca.on.ehealth.assessment.model.dto.PatientDto;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;

@Log
public class PatientControllerTest  extends AbstractIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Transactional
    void createPatient() {
        PatientDto patientDto = new PatientDto("Zach1","Galatians", new Date());
        ResponseEntity<PatientDto> entity = restTemplate.postForEntity("/v1/patients",patientDto,PatientDto .class);
        PatientDto savedPatientDto = entity.getBody();
        log.log(Level.OFF, Objects.requireNonNull(savedPatientDto).toString());
        Assertions.assertEquals(HttpStatus.OK,entity.getStatusCode());
        Assertions.assertNotNull(savedPatientDto.getId());
    }

    @Test
    @Transactional
    void createPatientValidation() {
        PatientDto patientDto = new PatientDto(null,"Galatians", null);
        ResponseEntity<ErrorMessage> entity = restTemplate.postForEntity("/v1/patients",patientDto,ErrorMessage .class);
        log.log(Level.OFF, Objects.requireNonNull(entity.getBody()).getErrors().toString());
        Assertions.assertNotEquals(entity.getStatusCode(),HttpStatus.OK);
    }

    @Test
    @Transactional
    void listAllPatients(){
        PatientDto patientDto = new PatientDto("Zach1","Galatians", new Date());
        restTemplate.postForEntity("/v1/patients",patientDto,PatientDto .class);
        patientDto = new PatientDto("York","Ii", new Date());
        restTemplate.postForEntity("/v1/patients",patientDto,PatientDto .class);
    }
}