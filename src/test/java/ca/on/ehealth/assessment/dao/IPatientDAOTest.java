package ca.on.ehealth.assessment.dao;

import ca.on.ehealth.assessment.AbstractIntegrationTest;
import ca.on.ehealth.assessment.model.entity.Patient;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;


@Log
public class IPatientDAOTest extends AbstractIntegrationTest {
    @Autowired
    private IPatientDAO iPatientDAO;

    @SneakyThrows
    @Test
    @Transactional
    public void shouldSavePatient(){
        Date birthday = new SimpleDateFormat("yyyy-MM-dd").parse("1983-04-21");
        Patient patient = new Patient("Zach1","Galatians", birthday);
        patient = iPatientDAO.save(patient);
        log.log(Level.OFF, "Patient: "+ patient);
        Assertions.assertNotNull(patient.getId());
    }
}
