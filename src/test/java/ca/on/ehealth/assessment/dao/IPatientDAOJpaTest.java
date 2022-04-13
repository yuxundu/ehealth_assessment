package ca.on.ehealth.assessment.dao;

import ca.on.ehealth.assessment.model.entity.Patient;
import ca.on.ehealth.assessment.util.PageConveter;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

@ActiveProfiles("test")
@Transactional
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Log
public class IPatientDAOJpaTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private IPatientDAO iPatientDAO;
    @Test
    void injectedComponentsAreNotNull(){
        Assertions.assertNotNull(dataSource);
    }

    @Test
    public void savePatient_success(){
        Patient patient = new Patient("Zach1","Galatians", new Date());
        patient = iPatientDAO.save(patient);
        log.log(Level.OFF, "Patient: "+ patient);
        Assertions.assertNotNull(patient.getId());
    }

    /**
     * Test find patients by first name
     */
    @Test
    public void findByFirstName_success(){
        iPatientDAO.save(new Patient("Zach1","Galatians", new Date()));
        Page<Patient> page = iPatientDAO.findByFirstName("Zach1", PageConveter.convert(null,null,null));
        List<Patient> patientList = page.getContent();
        Patient patient = patientList.get(0);
        Assertions.assertNotNull(patientList);
        Assertions.assertNotNull(patient.getId());
        Assertions.assertEquals(patient.getFirstName(),"Zach1");

    }
}
