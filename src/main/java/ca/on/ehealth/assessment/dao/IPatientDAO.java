package ca.on.ehealth.assessment.dao;

import ca.on.ehealth.assessment.model.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Patient data access object
 * @author yuxundu
 */
@Repository
public interface IPatientDAO extends IBaseJpaRepositoryDAO<Patient>{
    Page<Patient> findByFirstName(String firstName, Pageable pageable);
}


