package ca.on.ehealth.assessment.service.impl;

import ca.on.ehealth.assessment.dao.IPatientDAO;
import ca.on.ehealth.assessment.model.entity.Patient;
import ca.on.ehealth.assessment.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class PatientServiceImpl implements IPatientService {
    @Autowired
    private IPatientDAO patientDAO;

    @Override
    @Transactional
    public Patient addPatient( Patient patient) {
        patient.setId(null);
        patient =  patientDAO.save(patient);
        return patient;
    }

    @Override
    @Transactional
    public Page<Patient> getAllPatients(Pageable pageable) {
        return patientDAO.findAll(pageable);
    }

    @Override
    public Page<Patient> getPatientsByFirstName(String firstName, Pageable pageable) {
        return patientDAO.findByFirstName(firstName, pageable);
    }

    @Override
    public Patient getPatient(Long id) {
        return patientDAO.getById(id);
    }
}
