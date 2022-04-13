package ca.on.ehealth.assessment.service;

import ca.on.ehealth.assessment.model.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Patient Service Interface
 * @author yuxundu
 */
public interface IPatientService {
    /**
     * add new patient to database
     * @param patient  new DO
     * @return DO
     */
    Patient addPatient(Patient patient);

    /**
     * get all patients pagination
     * @param pageable  search page object
     * @return page object
     */
    Page<Patient> getAllPatients(Pageable pageable);

    /**
     * search patients by first name or get all
     * @param firstName requird
     * @param pageable page info
     * @return page object
     */
    Page<Patient> getPatientsByFirstName(String firstName,Pageable pageable);

    /**
     * get the specific patient
     * @param id key id
     * @return patient
     */
    Patient getPatient(Long id);
}
