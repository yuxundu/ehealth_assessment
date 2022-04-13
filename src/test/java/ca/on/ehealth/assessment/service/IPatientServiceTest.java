package ca.on.ehealth.assessment.service;

import ca.on.ehealth.assessment.model.entity.Patient;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Log
public class IPatientServiceTest {
    @Autowired
    private IPatientService patientService;

    @SneakyThrows
    @Test
    @Transactional
    public void testAddPatient(){
        Date birthday = new SimpleDateFormat("yyyy-MM-dd").parse("1983-04-21");
        Patient patient = new Patient("Zach1","Galatians", birthday);
        patient = patientService.addPatient(patient);
        log.log(Level.OFF,patient.toString());
        Assertions.assertNotNull(patient.getId());
    }


}