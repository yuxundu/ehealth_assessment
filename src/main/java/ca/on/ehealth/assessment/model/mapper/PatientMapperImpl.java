package ca.on.ehealth.assessment.model.mapper;

import ca.on.ehealth.assessment.model.dto.PatientDto;
import ca.on.ehealth.assessment.model.entity.Patient;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implement Patient Mapper
 * @author yuxundu
 */
@Component
public class PatientMapperImpl implements PatientMapper {
    @Override
    public PatientDto toDto(Patient patient) {
        if (patient == null){
            return null;
        }
        PatientDto patientDto = new PatientDto();
        BeanUtils.copyProperties(patient,patientDto);
        return patientDto;
    }

    @Override
    public Patient toDo(PatientDto patientDto) {
        if(patientDto == null){
            return null;
        }
        Patient patient = new Patient();
        BeanUtils.copyProperties(patientDto,patient);
        return patient;
    }

    @Override
    public List<PatientDto> toDtos(List<Patient> patients) {
        if(patients == null){
            return new ArrayList<PatientDto>();
        }
        List<PatientDto> list = new ArrayList<>();
        for (Patient patient : patients) {
            PatientDto patientDto = toDto(patient);
            list.add(patientDto);
        }
        return list;
    }

    @Override
    public Page<PatientDto> toDtoPage(Page<Patient> patientPage) {
        if(patientPage == null){
            return new PageImpl(new ArrayList<PatientDto>()) {
            };
        }
        List<PatientDto> patientDtoList = toDtos(patientPage.getContent());
        return new PageImpl(patientDtoList,patientPage.getPageable(),patientPage.getTotalElements() );
    }
}
