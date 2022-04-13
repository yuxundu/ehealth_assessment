package ca.on.ehealth.assessment.model.mapper;

import ca.on.ehealth.assessment.model.dto.PatientDto;
import ca.on.ehealth.assessment.model.entity.Patient;
import org.springframework.data.domain.Page;

import java.util.List;


/**
 * patient conversion
 * @author yuxundu
 */
public interface PatientMapper {

    PatientDto toDto(Patient patient);

    Patient toDo(PatientDto patientDto);

    List<PatientDto> toDtos(List<Patient> patients);

    Page<PatientDto> toDtoPage(Page<Patient> patientPage);

}
